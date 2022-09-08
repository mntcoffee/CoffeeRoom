package com.example.coffeeroom.ui.coffeeList

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.coffeeroom.MainApplication
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CoffeeListFragment : Fragment() {

    private val coffeeListViewModel: CoffeeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coffee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // sample data
        val coffee = Coffee(
//            id = 2L,
            createdAt = Date(),
            updatedAt = Date(),
            isFavorite = true,
            country = "Columbia",
            farm = "ABC Farm",
            process = "Honey",
            roaster = "ABC roaster",
            roastingDegree = "medium",
            comment = "It is very delicious.")

        coffeeListViewModel.allCoffee.observe(viewLifecycleOwner, Observer { allCoffee ->
            Log.d("test", allCoffee.toString())
        })

//        coffeeListViewModel.deleteAll()
//        coffeeListViewModel.add(coffee)
//        coffeeListViewModel.add(coffee)
//        coffeeListViewModel.update(coffee)
//        coffeeListViewModel.delete(coffee)
    }
}