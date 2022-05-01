package com.example.allin

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.ClothingTopsListDirections.Companion.actionClothingTopsListToAddClothingToOutfits
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_clothing_tops_list.view.*
import kotlinx.android.synthetic.main.grid_clothing_item.view.*


class ClothingTopsList : Fragment() {

    private val args by navArgs<ClothingTopsListArgs>()
    /**
     * Use this to get the query form Database of Tops
     */
    private lateinit var mClosetViewModel: ClosetViewModel
    val outfit: Outfit = args.outfit
    var adapter = ClothingTopsAdapter(outfit)


    //This class should only display Clothing Tops in a RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clothing_tops_list, container, false)

        //instantiate the recyclerView
        val recyclerView = view.clothing_top_rv
        //asssign the adapter
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        //Assign the correct data of Tops to the adapter of the RecyclerView
        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        mClosetViewModel.selectClothingTops().observe(viewLifecycleOwner, Observer { tops ->
            adapter.setData(tops)
            }
        )

        return view
    }

}

/**
 * This page consists of all code for the RecyclerView of Clothing Tops for selection only to add to outfits.
 */
class ClothingTopsAdapter(var outfit: Outfit) : RecyclerView.Adapter<ClothingTopsAdapter.MyViewHolder>() {
    private var clothingTopList = emptyList<Clothing>()

    inner class MyViewHolder(item: View): RecyclerView.ViewHolder(item){

    }

    //This inflates the EXACT SAME LAYOUT as ClothingList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_clothing_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = clothingTopList[position]
        holder.itemView.gl_clothing_type.text = currentItem.type
        holder.itemView.gl_clothing_color.text = currentItem.color
        holder.itemView.gl_clothing_style.text = currentItem.style
        holder.itemView.gl_clothing_brand.text = currentItem.brand
        holder.itemView.gl_clothing_item_photo.setImageURI( Uri.parse(currentItem.image))
        holder.itemView.rowLayout.setOnClickListener {
            //Provide the user with instruction to select an Outfit
            val action = actionClothingTopsListToAddClothingToOutfits(outfit,currentItem)
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return clothingTopList.size
    }

    fun setData(clothing: List<Clothing>) {
        this.clothingTopList = clothing
        notifyDataSetChanged()
    }

}