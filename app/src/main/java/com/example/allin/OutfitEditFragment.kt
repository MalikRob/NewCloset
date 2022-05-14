package com.example.allin

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_outfit_edit.view.*
import kotlinx.android.synthetic.main.grid_clothing_item.view.*


class OutfitEditFragment : Fragment() {

    private val args: OutfitEditFragmentArgs by navArgs()
    private lateinit var mClosetViewModel: ClosetViewModel
    private val adapter = OutfitEditAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_outfit_edit, container, false)

        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.selected_outfit_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val selectedOutfit = args.selectedOutfit
        //Toast.makeText(requireContext(), "Outfit: $selectedOutfit, outfitName: ${selectedOutfit?.outfitName} and OutfitID: ${selectedOutfit?.id}", Toast.LENGTH_SHORT).show()

        view.selected_outfit_name.text = selectedOutfit?.outfitName

        mClosetViewModel.getAllOutfitsWithClothing(selectedOutfit?.id!!).observe(viewLifecycleOwner, Observer { outfit ->
            adapter.setData(outfit.first().clothingList)
        })

        //setHasOptionsMenu(true)
        return view
    }

}

class OutfitEditAdapter: RecyclerView.Adapter<OutfitEditAdapter.MyViewHolder>() {
    private var clothingList = emptyList<Clothing>()
    lateinit var selectedItem: Clothing
    var isSelected: Boolean = false

    inner class MyViewHolder(item: View): RecyclerView.ViewHolder(item){
        var checkBox: CheckBox = item.findViewById(R.id.clothing_cb)
    }

    //This inflates the EXACT SAME LAYOUT as ClothingList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_clothing_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = clothingList[position]
        holder.itemView.gl_clothing_type.text = currentItem.type
        holder.itemView.gl_clothing_item_photo.setImageURI( Uri.parse(currentItem.image))

    }

    override fun getItemCount(): Int {
        return clothingList.size
    }

    fun setData(clothing: List<Clothing>) {
        this.clothingList = clothing
        notifyDataSetChanged()
    }

}