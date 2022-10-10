package com.example.coffeeroom.ui.coffeeList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeListFragment : Fragment() {

    private var _binding: FragmentCoffeeListBinding? = null
    private val binding get() = _binding!!

    private val coffeeListViewModel: CoffeeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        // すべてのコーヒーを監視
        coffeeListViewModel.allCoffee.observe(viewLifecycleOwner) { allCoffee ->
            allCoffee.let { adapter.submitList(it) }
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
