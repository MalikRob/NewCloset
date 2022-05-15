package com.example.allin

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.model.Clothing
import com.example.allin.model.Packing
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_travel_list.view.*
import kotlinx.android.synthetic.main.grid_clothing_item.view.*
import kotlinx.android.synthetic.main.packing_list_row.view.*

class TravelListFragment : Fragment() {

    private lateinit var mClosetViewModel: ClosetViewModel
    private var adapter = TravelListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_travel_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.packing_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        mClosetViewModel.readAllPackingData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        view.packingList_floating_btn.setOnClickListener {
            // Add new PackingList Items
            findNavController().navigate(R.id.action_travelListFragment_to_addPackingListItemFragment)
            Toast.makeText(
                requireContext(),
                "Adding New Packing List Item",
                Toast.LENGTH_SHORT
            ).show()

        }

        setHasOptionsMenu(true)
        return view
    }

}

class TravelListAdapter : RecyclerView.Adapter<TravelListAdapter.MyViewHolder>() {

    private var packingList = emptyList<Packing>()

    inner class MyViewHolder(item: View): RecyclerView.ViewHolder(item){

    }

    //This inflates the EXACT SAME LAYOUT as ClothingList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.packing_list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = packingList[position]

        holder.itemView.packing_list_name.text = currentItem.packingListName

    }

    override fun getItemCount(): Int {
        return packingList.size
    }

    fun setData(packingList: List<Packing>) {
        this.packingList = packingList
        notifyDataSetChanged()
    }

}