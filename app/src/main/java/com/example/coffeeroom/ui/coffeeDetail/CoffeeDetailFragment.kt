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

        // set text from coffee[coffeeID]
        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            binding.apply {
                textviewTitle.text = coffeeDetail.title
                textviewCountryData.text = coffeeDetail.country
                textviewFarmData.text = coffeeDetail.farm
                textviewProcessData.text = coffeeDetail.process
                textviewRoasterData.text = coffeeDetail.roaster
                textviewRoastingDegreeData.text = coffeeDetail.roastingDegree
                textviewComment.text = coffeeDetail.comment
                textviewUpdate.text = getString(R.string.updated_at, coffeeDetail.updatedAt)
            }
        }

        // edit button
        binding.buttonEdit.setOnClickListener {
            val action = CoffeeDetailFragmentDirections
                .actionCoffeeDetailFragmentToCoffeeDetailEditFragment(coffeeID)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}