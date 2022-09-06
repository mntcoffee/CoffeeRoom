package com.example.coffeeroom.ui.coffeeList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.coffeeroom.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeListFragment : Fragment() {

    private val coffeeListViewModel: CoffeeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coffee_list, container, false)
    }
}