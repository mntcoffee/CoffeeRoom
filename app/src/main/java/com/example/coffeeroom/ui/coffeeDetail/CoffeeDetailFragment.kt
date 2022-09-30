package com.example.coffeeroom.ui.coffeeDetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoffeeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // coffeeID(if add: id=0, edit: id=databaseID)
        val coffeeID = args.coffeeID
        coffeeDetailViewModel.onStart(coffeeID)

        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            setCoffeeDetail(coffeeDetail)
        }

        // edit button
        binding.buttonEdit.setOnClickListener {
            val action = CoffeeDetailFragmentDirections
                .actionCoffeeDetailFragmentToCoffeeDetailEditFragment(coffeeID)
            findNavController().navigate(action)
        }

        // favorite button
        binding.togglebuttonFavorite.setOnCheckedChangeListener { _, isChecked ->
            coffeeDetailViewModel.updateFavorite(isChecked)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCoffeeDetail(coffee: Coffee) {
        var title = coffee.title!!
        var country = coffee.country!!
        var farm = coffee.farm!!
        var process = coffee.process!!
        var roaster = coffee.roaster!!
        var roastingDegree = coffee.roastingDegree!!
        var comment = coffee.comment!!
        val uri: Uri? = coffee.image

        if(title.isBlank()) title = getString(R.string.untitled)
        if(country.isBlank()) country = getString(R.string.unknown)
        if(farm.isBlank()) farm = getString(R.string.unknown)
        if(process.isBlank()) process = getString(R.string.unknown)
        if(roaster.isBlank()) roaster = getString(R.string.unknown)
        if(roastingDegree.isBlank()) roastingDegree = getString(R.string.unknown)
        if(comment.isBlank()) comment = getString(R.string.not_yet_commented)

        // set textView
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
            Log.d("uri", "${uri.toString().isNullOrBlank()}")
            Log.d("uri", "coffee: $coffee")
            if(uri != null) {
                Log.d("uri", "nonnull: called")
                val bitmap = cameraViewModel.uriToBitmap(uri, requireContext())
                imageviewCoffee.setImageBitmap(cameraViewModel.rotateBitmap(bitmap))
            } else {
                Log.d("uri", "null: called")
                imageviewCoffee.setImageResource(R.drawable.coffee_image_default)
            }
        }
    }
}

