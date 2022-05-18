package com.example.allin

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.model.Clothing
import com.example.allin.model.FavoriteClothingCrossRef
import com.example.allin.model.Outfit
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_packing_list_choose_outfits.view.*
import kotlinx.android.synthetic.main.grid_clothing_item.view.*
import kotlinx.android.synthetic.main.outfit_custom_row.view.*

class SelectOutfitsForPackingList : Fragment() {

    private lateinit var mClosetViewModel: ClosetViewModel
    private lateinit var adapter: SelectOutfitsForPackingAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_packing_list_choose_outfits, container, false)

        recyclerView = view.packing_outfits_rv

        adapter = SelectOutfitsForPackingAdapter(requireParentFragment())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //OutfitViewModel
        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        mClosetViewModel.readAllOutfitData.observe(viewLifecycleOwner, Observer { outfit ->
            adapter.setData(outfit)
        })

        return view
    }
}

class SelectOutfitsForPackingAdapter(private val fragment: Fragment): RecyclerView.Adapter<SelectOutfitsForPackingAdapter.MyViewHolder>(){

    /**
     * Outfits will be stored into the Outfit Table in our DB.
     */
    private var outfitList = emptyList<Outfit>()
    private val mClosetViewModel: ClosetViewModel = ViewModelProvider(fragment).get(ClosetViewModel::class.java)

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val outfitName: TextView = itemView.OutfitName
        val outfitTheme: TextView = itemView.outfit_theme
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
        holder.itemView.outfit_cb.isVisible = false
        holder.outfitName.text = currentItem.outfitName
        holder.outfitTheme.text = currentItem.theme

        //When user selects options button. Popup menu of items becomes visible.
        holder.itemView.packing_options_btn.setOnClickListener{
            val cardOptionsBtn = holder.itemView.packing_options_btn
            popUpMenuOptionsSelected(cardOptionsBtn, fragment, currentItem)
        }
        //When user selects the cardview the user can view the outfit and Clothes in the outfit.
        holder.itemView.outfit_row_card.setOnClickListener {
            Toast.makeText(fragment.requireContext(), "User selected Outfit to view clothing items ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return outfitList.size
    }

    fun setData(outfit: List<Outfit>) {
        this.outfitList = outfit
        notifyDataSetChanged()
    }

    private fun popUpMenuOptionsSelected(
        cardOptionsBtn: ImageButton,
        fragment: Fragment,
        currentItem: Outfit
    ) {
        val popupMenu: PopupMenu = PopupMenu(fragment.requireContext(), cardOptionsBtn)
        popupMenu.menuInflater.inflate(R.menu.grid_clothing_popup,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.addToFavMenuItem -> {
                    Toast.makeText(fragment.requireContext(), "You added ${currentItem.outfitName}: to the Favorites List ", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popupMenu.show()
    }
}