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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeroom.MainApplication
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CoffeeListFragment : Fragment() {

    private var _binding: FragmentCoffeeListBinding? = null
    private val binding get() = _binding!!

    private val coffeeListViewModel: CoffeeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoffeeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        val list = binding.recyclerviewCoffeeList
        val adapter = CoffeeListAdapter()
        adapter.setOnItemClickListener(
            object : CoffeeListAdapter.OnItemClickListener {
                override fun onClick(coffee: Coffee) {
                    Log.d("test", coffee.toString())
                    val id: Long = coffee.id
//                    val action = CoffeeListFragmentDirections.actionCoffeeListFragmentToCoffeeDetailFragment(id)
                    val action = CoffeeListFragmentDirections
                        .actionCoffeeListFragmentToCoffeeDetailFragment(id)
                    findNavController().navigate(action)
                }
            }
        )
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)

        // sample data
        val coffee = Coffee(
            id = 0L,
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
            allCoffee.let { adapter.submitList(it) }
            Log.d("test", allCoffee.toString())
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}