package com.example.coffeeroom.ui.coffeeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.databinding.FragmentCoffeeDetailEditBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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
        val coffeeID = args.coffeeID
        // 編集モード(IDが0ではない)場合はIDのコーヒー情報をセット
        if(coffeeID != 0L) {
            coffeeDetailViewModel.onStart(args.coffeeID)
        }

        coffeeDetailViewModel.coffeeDetail.observe(viewLifecycleOwner) { coffeeDetail ->
            binding.apply {
                edittextTitle.editText?.setText(coffeeDetail.title)
                edittextCountry.editText?.setText(coffeeDetail.country)
                edittextFarm.editText?.setText(coffeeDetail.farm)
                edittextProcess.editText?.setText(coffeeDetail.process)
                edittextRoaster.editText?.setText(coffeeDetail.roaster)
                edittextRoastingDegree.editText?.setText(coffeeDetail.roastingDegree)
                edittextComment.editText?.setText(coffeeDetail.comment)
            }
        }

        binding.buttonSave.setOnClickListener {
            // create coffee from EditText

            val coffee = Coffee(
                    id = coffeeID,
                    createdAt = Date(),
                    updatedAt = Date(),
                    isFavorite = false,
                    title = binding.edittextTitle.editText?.text.toString(),
                    country = binding.edittextCountry.editText?.text.toString(),
                    farm = binding.edittextFarm.editText?.text.toString(),
                    process = binding.edittextProcess.editText?.text.toString(),
                    roaster = binding.edittextRoaster.editText?.text.toString(),
                    roastingDegree = binding.edittextRoastingDegree.editText?.text.toString(),
                    comment = binding.edittextComment.editText?.text.toString()
                )

//            Log.d("test", binding.edittextTitle.editText?.text.toString())
            if(coffeeID == 0L) {
                Log.d("test", "add: ${coffee.toString()}")
                coffeeDetailViewModel.add(coffee)
                findNavController().popBackStack()
            } else {
                Log.d("test", "edit: ${coffee.toString()}")
                coffeeDetailViewModel.update(coffee)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
