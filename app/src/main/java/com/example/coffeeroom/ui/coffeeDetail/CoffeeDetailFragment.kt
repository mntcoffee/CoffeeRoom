package com.example.coffeeroom.ui.coffeeDetail

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.R
import com.example.coffeeroom.databinding.FragmentCoffeeDetailBinding
import com.example.coffeeroom.databinding.FragmentCoffeeDetailEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeDetailFragment : Fragment() {

    private var _binding: FragmentCoffeeDetailBinding? = null
    private val binding get() = _binding!!

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModels()

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

        // coffee ID
        val coffeeID = args.coffeeID
        coffeeDetailViewModel.onStart(coffeeID)

        // set info from coffee[coffeeID]
        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            // if not input data, set some text
            var title = coffeeDetail.title!!
            var country = coffeeDetail.country!!
            var farm = coffeeDetail.farm!!
            var process = coffeeDetail.process!!
            var roaster = coffeeDetail.roaster!!
            var roastingDegree = coffeeDetail.roastingDegree!!
            var comment = coffeeDetail.comment!!

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
                textviewUpdate.text = getString(R.string.updated_at, coffeeDetail.updatedAt)
                togglebuttonFavorite.isChecked = coffeeDetail.isFavorite
            }
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
}