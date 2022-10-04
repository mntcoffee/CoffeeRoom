package com.example.coffeeroom.ui.coffeeDetail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeDetailBinding
import com.example.coffeeroom.ui.camera.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeDetailFragment : Fragment() {

    private var _binding: FragmentCoffeeDetailBinding? = null
    private val binding get() = _binding!!

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()

    private val args: CoffeeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // coffeeID(if add: id=0, edit: id=databaseID)
        val coffeeID = args.coffeeID
        coffeeDetailViewModel.onStart(coffeeID)

        // コーヒー情報が更新された場合画面を更新
        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            setCoffeeDetail(coffeeDetail)
        }

        // 編集ボタン
        binding.buttonEdit.setOnClickListener {
            val action = CoffeeDetailFragmentDirections
                .actionCoffeeDetailFragmentToCoffeeDetailEditFragment(coffeeID)
            findNavController().navigate(action)
        }

        // お気に入りボタン
        binding.togglebuttonFavorite.setOnCheckedChangeListener { _, isChecked ->
            coffeeDetailViewModel.updateFavorite(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // コーヒー情報を画面に表示
    private fun setCoffeeDetail(coffee: Coffee) {
        var title = coffee.title!!
        var country = coffee.country!!
        var farm = coffee.farm!!
        var process = coffee.process!!
        var roaster = coffee.roaster!!
        var roastingDegree = coffee.roastingDegree!!
        var comment = coffee.comment!!
        val uri: Uri? = coffee.image

        // データが空白の場合デフォルトの文字列を表示
        if(title.isBlank()) title = getString(R.string.untitled)
        if(country.isBlank()) country = getString(R.string.unknown)
        if(farm.isBlank()) farm = getString(R.string.unknown)
        if(process.isBlank()) process = getString(R.string.unknown)
        if(roaster.isBlank()) roaster = getString(R.string.unknown)
        if(roastingDegree.isBlank()) roastingDegree = getString(R.string.unknown)
        if(comment.isBlank()) comment = getString(R.string.not_yet_commented)

        // textViewにデータを表示
        binding.apply {
            textviewTitle.text = title
            textviewCountryData.text = country
            textviewFarmData.text = farm
            textviewProcessData.text = process
            textviewRoasterData.text = roaster
            textviewRoastingDegreeData.text = roastingDegree
            textviewComment.text = comment
            textviewUpdate.text = getString(R.string.updated_at, coffee.updatedAt)
            togglebuttonFavorite.isChecked = coffee.isFavorite
            // コーヒーの画像がある場合はそれを表示，ない場合はデフォルト画像を表示
            if(uri != null) {
                val bitmap = cameraViewModel.uriToBitmap(uri, requireContext())
                imageviewCoffee.setImageBitmap(cameraViewModel.rotateBitmap(bitmap))
            } else {
                imageviewCoffee.setImageResource(R.drawable.coffee_image_default)
            }
        }
    }
}
