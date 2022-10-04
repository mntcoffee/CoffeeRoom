package com.example.coffeeroom.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.databinding.FragmentCameraResultBinding

class CameraResultFragment : Fragment() {

    private var _binding: FragmentCameraResultBinding? = null
    private val binding get() = _binding!!

    private val cameraViewModel: CameraViewModel by viewModels()

    private val args: CameraResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 前のfragmentで撮影した画像を表示
        val uri = args.cameraResultURI.toUri()
        val bitmap = cameraViewModel.uriToBitmap(uri, requireContext())
        binding.imageviewCameraResult.setImageBitmap(cameraViewModel.rotateBitmap(bitmap))

        // キャンセルボタン
        binding.buttonCancelCameraResult.setOnClickListener {
            findNavController().popBackStack()
        }

        // 適用ボタン
        binding.buttonApplyCameraResult.setOnClickListener {
            // コーヒー編集画面に戻る際に画像を送る
            val navigation = findNavController()
            navigation.previousBackStackEntry?.savedStateHandle?.set("camera", uri)
            navigation.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
