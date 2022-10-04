package com.example.coffeeroom.ui.coffeeDetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CoffeeDetailEditFragment : Fragment(), SetImageDialogFragment.NoticeDialogListener {

    private var _binding: FragmentCoffeeDetailEditBinding? = null
    private val binding get() = _binding!!

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()

    private val args: CoffeeDetailEditFragmentArgs by navArgs()

    // dialog(take photo or select from folder)
    private lateinit var dialog: DialogFragment

    // folderから選択した画像をセット
    private val launcher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        if(it != null) {
            coffeeDetailViewModel.setImage(it)
        }
    }

    // navigation
    private val navigation: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeDetailEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 表示するコーヒーのデータベースID
        val coffeeID = args.coffeeID
        // IDが0なら追加モード，0以外なら編集モード
        coffeeDetailViewModel.isEditMode = (coffeeID != 0L)

        // 編集モードなら画面を初期化
        if(coffeeDetailViewModel.isEditMode) {
            // データベースのコーヒーをViewModelに格納
            coffeeDetailViewModel.onStart(coffeeID)
            // 画面をデータベースの情報をもとに更新
            coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffee ->
                setCoffeeDetail(coffee)
            }
        } else {
            // 追加モードの場合デフォルトの画像を表示
            binding.imageviewCoffee.setImageResource(R.drawable.coffee_image_default)
        }

        // take photoから戻ってきた時のcallback
        observeNavigationCallBack()

        // 保存ボタン
        binding.buttonSave.setOnClickListener {
            saveDataBase(coffeeDetailViewModel.isEditMode)
        }

        // coffeeImageが変更された場合画面を更新
        coffeeDetailViewModel.coffeeImage.observe(viewLifecycleOwner) { uri ->
            val bitmap = uri?.let {
                cameraViewModel.uriToBitmap(it, requireContext())
            }
            binding.imageviewCoffee.setImageBitmap(bitmap?.let {
                cameraViewModel.rotateBitmap(it)
            })
        }

        // dialogを表示(take photo or select from folder)
        binding.imageviewCoffee.setOnClickListener {
            dialog = SetImageDialogFragment()
            dialog.show(childFragmentManager, "image")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 写真を取る場合
    override fun onDialogCameraClick(dialog: DialogFragment) {
        dialog.dismiss()
        val action = CoffeeDetailEditFragmentDirections
            .actionCoffeeDetailEditFragmentToCameraFragment()
        navigation.navigate(action)
    }

    // フォルダから選択する場合
    override fun onDialogFolderClick(dialog: DialogFragment) {
        dialog.dismiss()
        launcher.launch(arrayOf("image/*"))
    }

    // 画像選択のcallback
    private fun observeNavigationCallBack() {
        Log.d("callback", "call")
        navigation.currentBackStackEntry?.savedStateHandle?.getLiveData<Uri>("camera")
            ?.observe(viewLifecycleOwner) {
                coffeeDetailViewModel.setImage(it)
            }
    }

    // コーヒー情報を画面に表示
    private fun setCoffeeDetail(coffee: Coffee) {
        binding.apply {
            edittextTitle.editText?.setText(coffee.title)
            edittextCountry.editText?.setText(coffee.country)
            edittextFarm.editText?.setText(coffee.farm)
            edittextProcess.editText?.setText(coffee.process)
            edittextRoaster.editText?.setText(coffee.roaster)
            edittextRoastingDegree.editText?.setText(coffee.roastingDegree)
            edittextComment.editText?.setText(coffee.comment)

            // 撮影した画像orフォルダから選択した画像がある場合はそれを表示
            // データベースの画像がある場合はそれを表示
            // いずれでもない場合はデフォルト画像を表示
            if(coffeeDetailViewModel.coffeeImage.value != null) {
                val bitmap = cameraViewModel.uriToBitmap(
                    coffeeDetailViewModel.coffeeImage.value!!,
                    requireContext()
                )
                imageviewCoffee.setImageBitmap(cameraViewModel.rotateBitmap(bitmap))
            } else if(coffee.image != null) {
                imageviewCoffee.setImageBitmap(
                    cameraViewModel.uriToBitmap(coffee.image, requireContext())
                )
                coffeeDetailViewModel.setImage(coffee.image)
            } else {
                imageviewCoffee.setImageResource(R.drawable.coffee_image_default)
            }
        }
    }

    // データベースに保存(更新)
    private fun saveDataBase(isEditMode: Boolean) {
        // 現在時刻を取得
        val updatedTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        val formattedUpdatedTime = updatedTime.format(formatter)
        // editTextのデータを取得
        val title = binding.edittextTitle.editText?.text.toString()
        val country = binding.edittextCountry.editText?.text.toString()
        val farm = binding.edittextFarm.editText?.text.toString()
        val process = binding.edittextProcess.editText?.text.toString()
        val roaster = binding.edittextRoaster.editText?.text.toString()
        val roastingDegree = binding.edittextRoastingDegree.editText?.text.toString()
        val comment = binding.edittextComment.editText?.text.toString()
        val coffeeImage = coffeeDetailViewModel.coffeeImage.value

        // 編集モードならupdateメソッド，追加モードならaddメソッド
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
            coffeeDetailViewModel.add(coffee)
        }
        navigation.popBackStack()
    }
}
