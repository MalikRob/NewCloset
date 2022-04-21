package com.example.allin.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Clothing

import kotlinx.android.synthetic.main.outfit_custom_row.view.*

class AltListAdapter: RecyclerView.Adapter<AltListAdapter.MyViewHolder>(){

    private var altclothingList = emptyList<Clothing>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AltListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.outfit_custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = altclothingList[position]
        holder.itemView.clothing_type_one.text = currentItem.type

//        holder.itemView.rowLayout.setOnClickListener() {
//            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
//            holder.itemView.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return altclothingList.size
    }

    fun setData(clothing: List<Clothing>) {
        this.altclothingList = clothing
        notifyDataSetChanged()
    }
}