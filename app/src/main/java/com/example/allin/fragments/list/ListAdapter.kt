package com.example.allin.fragments.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Clothing
import kotlinx.android.synthetic.main.grid_clothing_item.view.*
import kotlinx.android.synthetic.main.outfit_custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var clothingList = emptyList<Clothing>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    //Inflates view where the objects will be stored. XML that displays items in the row inside the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_clothing_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = clothingList[position]
        holder.itemView.gl_clothing_type.text = currentItem.type
        holder.itemView.gl_clothing_item_photo.setImageURI( Uri.parse(currentItem.image))
        holder.itemView.grid_item.setOnClickListener {
            val action = ListFragmentDirections.actionClothingListFragmentToUpdateClothingFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return clothingList.size
    }

    fun setData(clothing: List<Clothing>) {
        this.clothingList = clothing
        notifyDataSetChanged()
    }

}