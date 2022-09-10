package com.example.coffeeroom.ui.coffeeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.databinding.FragmentCoffeeDetailEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeDetailEditFragment : Fragment() {

    private var _binding: FragmentCoffeeDetailEditBinding? = null
    private val binding get() = _binding!!

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModels()

    private val args: CoffeeDetailEditFragmentArgs by navArgs()

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
        coffeeDetailViewModel.onStart(args.coffeeID)

        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            Log.d("test", "observe: ${coffeeDetail.toString()}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
