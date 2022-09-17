package com.example.coffeeroom.ui.coffeeDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeDetailEditBinding
import com.example.coffeeroom.ui.dialog.SetImageDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CoffeeDetailEditFragment : Fragment(), SetImageDialogFragment.NoticeDialogListener {

    private var _binding: FragmentCoffeeDetailEditBinding? = null
    private val binding get() = _binding!!

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModels()

    private val args: CoffeeDetailEditFragmentArgs by navArgs()

    private lateinit var dialog: DialogFragment

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

        Log.d("test", args.coffeeID.toString())
        // coffeeIDが0でないなら編集モード
        val coffeeID = args.coffeeID
        val isEditMode = (coffeeID != 0L)
        // 編集モード(IDが0ではない)場合はIDのコーヒー情報をセット
        if(isEditMode) {
            coffeeDetailViewModel.onStart(args.coffeeID)
        }

        // editTextにコーヒーデータをセット
        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            binding.apply {
                edittextTitle.editText?.setText(coffeeDetail.title)
                edittextCountry.editText?.setText(coffeeDetail.country)
                edittextFarm.editText?.setText(coffeeDetail.farm)
                edittextProcess.editText?.setText(coffeeDetail.process)
                edittextRoaster.editText?.setText(coffeeDetail.roaster)
                edittextRoastingDegree.editText?.setText(coffeeDetail.roastingDegree)
                edittextComment.editText?.setText(coffeeDetail.comment)
            }
        }

        binding.buttonSave.setOnClickListener {
            // get current time
            val updatedTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            val formattedUpdatedTime = updatedTime.format(formatter)
            Log.d("detail", formattedUpdatedTime)
            // create coffee info from EditText
            var title = binding.edittextTitle.editText?.text.toString()
            var country = binding.edittextCountry.editText?.text.toString()
            var farm = binding.edittextFarm.editText?.text.toString()
            var process = binding.edittextProcess.editText?.text.toString()
            var roaster = binding.edittextRoaster.editText?.text.toString()
            var roastingDegree = binding.edittextRoastingDegree.editText?.text.toString()
            var comment = binding.edittextComment.editText?.text.toString()

            // create updated coffee
            val coffee = Coffee(
                    id = coffeeID,
                    createdAt = coffeeDetailViewModel.coffeeDetail
                        .value?.createdAt ?: formattedUpdatedTime,
                    updatedAt = formattedUpdatedTime,
                    isFavorite = false,
                    title = title,
                    country = country,
                    farm = farm,
                    process = process,
                    roaster = roaster,
                    roastingDegree = roastingDegree,
                    comment = comment
                )

//            Log.d("test", binding.edittextTitle.editText?.text.toString())
            if(isEditMode) {
                Log.d("test", "edit: ${coffee.toString()}")
                coffeeDetailViewModel.update(coffee)
                findNavController().popBackStack()
            } else {
                Log.d("test", "add: ${coffee.toString()}")
                coffeeDetailViewModel.add(coffee)
                findNavController().popBackStack()
            }
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
        findNavController().navigate(action)
    }

    override fun onDialogFolderClick(dialog: DialogFragment) {
        Log.d("dialog", "folder")
        dialog.dismiss()
    }

}
