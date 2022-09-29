package com.example.coffeeroom.ui.coffeeDetail

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeDetailEditBinding
import com.example.coffeeroom.ui.camera.CameraViewModel
import com.example.coffeeroom.ui.dialog.SetImageDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CoffeeDetailEditFragment : Fragment(), SetImageDialogFragment.NoticeDialogListener {

    private var _binding: FragmentCoffeeDetailEditBinding? = null
    private val binding get() = _binding!!

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()

    private val args: CoffeeDetailEditFragmentArgs by navArgs()

    private lateinit var dialog: DialogFragment

    private val navigation: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoffeeDetailEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffeeID = args.coffeeID
        coffeeDetailViewModel.isEditMode = (coffeeID != 0L)

        // 編集ならデータベースのコーヒーをViewModelに格納
        if(coffeeDetailViewModel.isEditMode) {
            coffeeDetailViewModel.onStart(coffeeID)
            // 画面をデータベースの情報をもとに更新
            coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffee ->
                setCoffeeDetail(coffee)
            }
        }

        // callback from camera result
        observeNavigationCallBack()

        binding.buttonSave.setOnClickListener {
            saveDataBase(coffeeDetailViewModel.isEditMode)
        }

        coffeeDetailViewModel.coffeeImage.observe(viewLifecycleOwner) { bitmap ->
            binding.imageviewCoffee.setImageBitmap(bitmap)
        }

        // edit imageView
        binding.imageviewCoffee.setOnClickListener {
            dialog = SetImageDialogFragment()
            dialog.show(childFragmentManager, "image")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDialogCameraClick(dialog: DialogFragment) {
        Log.d("dialog", "camera")
        dialog.dismiss()
        val action = CoffeeDetailEditFragmentDirections
            .actionCoffeeDetailEditFragmentToCameraFragment()
        navigation.navigate(action)
    }

    override fun onDialogFolderClick(dialog: DialogFragment) {
        Log.d("dialog", "folder")
        dialog.dismiss()
    }

    private fun observeNavigationCallBack() {
        Log.d("callback", "call")
        navigation.currentBackStackEntry?.savedStateHandle?.getLiveData<Uri>("key")
            ?.observe(viewLifecycleOwner) {
                Log.d("test", it.toString())
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(BufferedInputStream(inputStream))
                cameraViewModel.rotateBitmap(bitmap)
                    ?.let { it -> coffeeDetailViewModel.setImage(it) }
            }
    }

    private fun setCoffeeDetail(coffee: Coffee) {
        binding.apply {
            edittextTitle.editText?.setText(coffee.title)
            edittextCountry.editText?.setText(coffee.country)
            edittextFarm.editText?.setText(coffee.farm)
            edittextProcess.editText?.setText(coffee.process)
            edittextRoaster.editText?.setText(coffee.roaster)
            edittextRoastingDegree.editText?.setText(coffee.roastingDegree)
            edittextComment.editText?.setText(coffee.comment)
            if(coffee.image == null) {
                imageviewCoffee.setImageResource(R.drawable.coffee_image_default)
            } else {
                imageviewCoffee.setImageBitmap(coffee.image)
            }
            // camera or folderから画像が返された場合はそれを表示
            if(coffeeDetailViewModel.coffeeImage.value != null) {
                imageviewCoffee.setImageBitmap(coffeeDetailViewModel.coffeeImage.value)
            }
        }
    }

    private fun saveDataBase(isEditMode: Boolean) {
        // get current time
        val updatedTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        val formattedUpdatedTime = updatedTime.format(formatter)
        // create coffee info from EditText
        var title = binding.edittextTitle.editText?.text.toString()
        var country = binding.edittextCountry.editText?.text.toString()
        var farm = binding.edittextFarm.editText?.text.toString()
        var process = binding.edittextProcess.editText?.text.toString()
        var roaster = binding.edittextRoaster.editText?.text.toString()
        var roastingDegree = binding.edittextRoastingDegree.editText?.text.toString()
        var comment = binding.edittextComment.editText?.text.toString()
        // get coffee image
        val coffeeImage = (binding.imageviewCoffee.drawable as BitmapDrawable)?.bitmap

        if(isEditMode) {
            val coffee = coffeeDetailViewModel.coffeeDetail.value?.copy(
                updatedAt = formattedUpdatedTime,
                image = coffeeImage,
                title = title,
                country = country,
                farm = farm,
                process = process,
                roaster = roaster,
                roastingDegree = roastingDegree,
                comment = comment
            )
            Log.d("update", "update: $coffee")
            coffeeDetailViewModel.update(coffee!!)

        } else {
            val coffee = Coffee(
                id = 0L,
                createdAt = formattedUpdatedTime,
                updatedAt = formattedUpdatedTime,
                isFavorite = false,
                image = coffeeImage,
                title = title,
                country = country,
                farm = farm,
                process = process,
                roaster = roaster,
                roastingDegree = roastingDegree,
                comment = comment
            )
            Log.d("add", "add: $coffee")
            coffeeDetailViewModel.add(coffee)
        }
        navigation.popBackStack()
    }
}
