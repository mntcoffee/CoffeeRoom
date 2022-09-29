package com.example.coffeeroom.ui.camera

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.databinding.FragmentCameraResultBinding
import com.example.coffeeroom.ui.coffeeDetail.CoffeeDetailViewModel
import java.io.BufferedInputStream

class CameraResultFragment : Fragment() {

    private var _binding: FragmentCameraResultBinding? = null
    private val binding get() = _binding!!

    private val cameraViewModel: CameraViewModel by viewModels()

    private val args: CameraResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCameraResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", "${args.toString()}")
        val uri = args.cameraResultURI.toUri()
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(BufferedInputStream(inputStream))
        binding.imageviewCameraResult.setImageBitmap(cameraViewModel.rotateBitmap(bitmap))

        binding.buttonCancelCameraResult.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonApplyCameraResult.setOnClickListener {
            val navigation = findNavController()
            navigation.previousBackStackEntry?.savedStateHandle?.set("key", uri)
            navigation.popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
