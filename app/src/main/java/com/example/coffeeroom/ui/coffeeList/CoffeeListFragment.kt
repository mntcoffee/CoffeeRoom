package com.example.coffeeroom.ui.coffeeList

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
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
                    val action = CoffeeListFragmentDirections
                        .actionCoffeeListFragmentToCoffeeDetailFragment(id)
                    findNavController().navigate(action)
                }
            }
        )
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)

        // sample data
//        val coffee = Coffee(
//            id = 0L,
//            createdAt = "2022/10/4 12:43",
//            updatedAt = "2022/10/6 14:11",
//            isFavorite = true,
//            title = "Columbia ABC Farm Neon Tet Angels Natural",
//            country = "Columbia",
//            farm = "ABC Farm",
//            process = "Natural",
//            roaster = "ABC roaster",
//            roastingDegree = "medium",
//            comment = "It is very delicious."
//        )

        coffeeListViewModel.allCoffee.observe(viewLifecycleOwner) { allCoffee ->
            allCoffee.let { adapter.submitList(it) }
            Log.d("test", allCoffee.toString())
        }

        coffeeListViewModel.filteredCoffeeList.observe(viewLifecycleOwner) { filteredCoffee ->
            adapter.submitList(filteredCoffee)
        }

        // 新規追加ボタン
        binding.fabAddCoffee.setOnClickListener {
            val action = CoffeeListFragmentDirections
                .actionCoffeeListFragmentToCoffeeDetailEditFragment(0L)
            findNavController().navigate(action)
        }

        // 検索ボタンを押したら検索画面(SearchResultFragment)に遷移する
        binding.toolbarCoffeeList.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    Log.d("search", "search")
                    val action = CoffeeListFragmentDirections
                        .actionCoffeeListFragmentToSearchResultFragment()
                    findNavController().navigate(action)
                }

            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
