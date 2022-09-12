package com.example.coffeeroom.ui.coffeeList

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeListBinding
import com.example.coffeeroom.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(), TextWatcher {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val searchResultViewModel: SearchResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        val list = binding.recyclerViewSearchResult
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

        // 画面生成時にキーボードを表示
        showSoftKeyboard(binding.edittextSearch)

        // RecyclerViewにfocusが当たったときにキーボードを隠す
        binding.recyclerViewSearchResult.setOnFocusChangeListener { _, HasFocus ->
            if(HasFocus) showOffKeyboard()
        }
        // RecyclerViewがタッチされたときにフォーカスを設定
        binding.recyclerViewSearchResult.setOnTouchListener { view, motionEvent ->
            Log.d("test", "touch")
            binding.recyclerViewSearchResult.requestFocus()
            true
        }

        binding.edittextSearch.addTextChangedListener(this)

        searchResultViewModel.searchedCoffee.observe(viewLifecycleOwner) { searchedList ->
            adapter.submitList(searchedList)
        }

        // back button
        binding.imageviewBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // キーボードを表示
    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    // キーボードを隠す
    private fun showOffKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        Log.d("search", "text: ${p0.toString()}")
        searchResultViewModel.searchCoffee(p0.toString())
    }
}
