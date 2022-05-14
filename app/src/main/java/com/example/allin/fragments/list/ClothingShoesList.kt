package com.example.allin.fragments.list

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_clothing_shoes_list.view.*
import kotlinx.android.synthetic.main.grid_clothing_item.view.*


class ClothingShoesList : Fragment() {

    val args: ClothingShoesListArgs by navArgs()
    private lateinit var mClosetViewModel: ClosetViewModel

    private var adapter = ClothingShoesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clothing_shoes_list, container, false)
        setHasOptionsMenu(true)

        val recyclerView = view.clothing_shoes_rv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        mClosetViewModel.selectAllShoes().observe(viewLifecycleOwner, Observer { shoes ->
            adapter.setData(shoes)
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_outfits_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_clothing_to_outfit_button){
            if(adapter.isSelected) {
                val selectedDialog = AlertDialog.Builder(this.requireContext())
                selectedDialog.setPositiveButton("Yes") { _, _ ->
                    val action =
                        ClothingShoesListDirections.actionClothingShoesListToAddClothingToOutfits(
                            args.currentOutfit,
                            args.currentTop,
                            args.currentBottom,
                            adapter.selectedItem,
                            args.currentOuterWear
                        )
                    findNavController().navigate(action)
                }
                selectedDialog.setNegativeButton("No") { _, _ -> }
                val temp = adapter.selectedItem.type
                selectedDialog.setTitle("Add $temp to the outfit?")
                Toast.makeText(this.requireContext(), "Added to Outfit", Toast.LENGTH_SHORT).show()
                selectedDialog.create().show()
            } else {
                Toast.makeText(this.requireContext(), "Please select an Item", Toast.LENGTH_LONG).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}

class ClothingShoesAdapter: RecyclerView.Adapter<ClothingShoesAdapter.MyViewHolder>() {
    private var clothingShoesList = emptyList<Clothing>()
    lateinit var selectedItem: Clothing
    var isSelected: Boolean = false

    inner class MyViewHolder(item: View): RecyclerView.ViewHolder(item){
        var checkBox: CheckBox = item.findViewById(R.id.clothing_cb)
    }

    //This inflates the EXACT SAME LAYOUT as ClothingList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_clothing_bot_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = clothingShoesList[position]

        holder.itemView.gl_clothing_type.text = currentItem.type
        holder.itemView.gl_clothing_item_photo.setImageURI( Uri.parse(currentItem.image))

        holder.itemView.grid_item.setOnClickListener {
            if (!holder.itemView.clothing_cb.isChecked){
                selectedItem = currentItem
                holder.itemView.clothing_cb.isChecked = true
                isSelected = true
            }else {
                holder.itemView.clothing_cb.isChecked = false
                isSelected = false
            }
        }
    }

    override fun getItemCount(): Int {
        return clothingShoesList.size
    }

    fun setData(clothing: List<Clothing>) {
        this.clothingShoesList = clothing
        notifyDataSetChanged()
    }

}