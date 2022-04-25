package com.example.allin.fragments.add.horizontal_lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Clothing
import kotlinx.android.synthetic.main.outfit__add_clothing_item_1.view.*

/**
 * This adapter is strictly for grabbing clothing items and storing them into the outfits.
 */
class ClothingItem1Adapter: RecyclerView.Adapter<ClothingItem1Adapter.MyViewHolder>() {

    private var clothingTopsList = emptyList<Clothing>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.outfit__add_clothing_item_1, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = clothingTopsList[position]
        holder.itemView.clothing_item_one_tops.text = currentItem.type
    }

    override fun getItemCount(): Int {
        return clothingTopsList.size
    }
}