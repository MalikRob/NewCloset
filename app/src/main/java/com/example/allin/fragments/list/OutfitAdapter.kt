package com.example.allin.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Outfit
import kotlinx.android.synthetic.main.outfit_custom_row.view.*

class OutfitAdapter: RecyclerView.Adapter<OutfitAdapter.MyViewHolder>(){

    /**
     * Outfits will be stored into the Outfit Table in our DB.
     */
    private var outfitList = emptyList<Outfit>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    //Inflates view where the objects will be stored. XML that displays items in the row inside the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.outfit_custom_row, parent, false)
        )
    }

    // For testing purposes, only the OutFit name will be displayed until we build the Standard
    // Update, Delete, Insert Queries
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = outfitList[position]
        holder.itemView.outfit_name.text = currentItem.outfitName

    }

    override fun getItemCount(): Int {
        return outfitList.size
    }

    fun setData(outfit: List<Outfit>) {
        this.outfitList = outfit
        notifyDataSetChanged()
    }
}