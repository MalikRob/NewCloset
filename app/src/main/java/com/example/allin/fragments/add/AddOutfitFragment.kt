package com.example.allin.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.fragments.add.horizontal_lists.ClothingItem1Adapter
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.fragment_add_outfit.*
import kotlinx.android.synthetic.main.fragment_add_outfit.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddOutfitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddOutfitFragment : Fragment() {

    private lateinit var mClothingViewModel: ClothingViewModel
    var topsAdapter = ClothingItem1Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_outfit, container, false)

        //Let the user add an OutfitName to the Edit Text

        //Inflate the RecyclerView 1 for ClothingTops
        val topRecyclerView = view.clothing_item_one_recyclerview
        topRecyclerView.adapter = topsAdapter
        topRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)

        // sets Tops data from Clothing in the first RecyclerView
        mClothingViewModel.selectClothingTops().observe(viewLifecycleOwner)
        { topsList ->
            topsList.let {
                topsAdapter.setTopData(it)
            }
        }

        // Add selected Items to list.
        view.add_button.setOnClickListener {
            insertNewOutfit()
        }
        return view
    }

    private fun insertNewOutfit() {
        val outfitName = outfit_name_et.text.toString()
    }

}