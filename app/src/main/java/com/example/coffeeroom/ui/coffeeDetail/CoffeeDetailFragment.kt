package com.example.coffeeroom.ui.coffeeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.R
import com.example.coffeeroom.databinding.FragmentCoffeeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeDetailFragment : Fragment() {

    private var _binding: FragmentCoffeeDetailBinding? = null
    val binding get() = _binding!!

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

        Log.d("test", args.coffeeID.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
