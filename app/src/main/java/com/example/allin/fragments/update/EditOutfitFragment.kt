package com.example.allin.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_edit_outfit.view.*

class EditOutfitFragment : Fragment() {

    private val args: EditOutfitFragmentArgs by navArgs()
    private lateinit var mClosetViewModel: ClosetViewModel
    private val adapter = EditingAdapter()

    //This argument is carrying a value of Outfit(id, outfitName)
    private lateinit var addTopButton: Button
    private lateinit var addBotButton: Button
    private lateinit var addShoesButton: Button
    private lateinit var addOuterWearButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_edit_outfit, container, false)

        //Takes the entered data
        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        //val editingOutfit = args.editingOutfit

        /**
         * Displays Outfit Title
         */
        //view.selected_outfit_title.text = editingOutfit?.outfitName

//        mClosetViewModel.getAllOutfitsWithClothing(editingOutfit?.id!!).observe(viewLifecycleOwner, Observer { outfit ->
//            adapter.setData(outfit.first().clothingList)
//        })

        setHasOptionsMenu(true)

        return view
    }

    //EditOutfitFragment Options Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.updating_outfit_menu, menu)
    }

}

class EditingAdapter: RecyclerView.Adapter<EditingAdapter.MyViewHolder>() {
    private var clothingList = emptyList<Clothing>()

    inner class MyViewHolder(item: View): RecyclerView.ViewHolder(item){
        var checkBox: CheckBox = item.findViewById(R.id.clothing_cb)
    }

    //This inflates the EXACT SAME LAYOUT as AddClothingToOutfits
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_clothing_to_outfits, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return clothingList.size
    }

    fun setData(clothing: List<Clothing>) {
        this.clothingList = clothing
        notifyDataSetChanged()
    }

}