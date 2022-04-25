package com.example.allin.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allin.R
import com.example.allin.fragments.add.horizontal_lists.ClothingItem1Adapter
import com.example.allin.viewmodel.ClothingViewModel

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
        return inflater.inflate(R.layout.fragment_add_outfit, container, false)

        //Let the user add an OutfitName to the Edit Text

        //Inflate the RecyclerView 1 for ClothingTops
        val recyclerView = view.clothing_item_one_recyclerview
        topsRecyclerView.adapter = adapter

        return view
    }

}