package com.example.allin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allin.R
import com.example.allin.model.Outfit
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_outfit_list.view.*
import kotlinx.android.synthetic.main.outfit_custom_row.view.*

class OutfitListFragment : Fragment() {

    private lateinit var mClosetViewModel: ClosetViewModel
    private var adapter = OutfitAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_outfit_list, container, false)

        //RecyclerView standard Layout for now.
        val recyclerView = view.outfit_recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //OutfitViewModel
        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        mClosetViewModel.readAllOutfitData.observe(viewLifecycleOwner, Observer { outfit ->
            adapter.setData(outfit)
        })

        //Add button adds new Outfit Item.
        view.add_outfit_btn.setOnClickListener{
            findNavController().navigate(R.id.action_outfitListFragment_to_addOutfitFragment)
        }
        //Add menu
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            //deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            //mClothingViewModel.deleteAllClothing()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything.",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}

class OutfitAdapter: RecyclerView.Adapter<OutfitAdapter.MyViewHolder>(){

    /**
     * Outfits will be stored into the Outfit Table in our DB.
     */
    private var outfitList = emptyList<Outfit>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val outfitName: TextView = itemView.OutfitName
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
        holder.outfitName.text = currentItem.outfitName
        holder.itemView.setOnClickListener{
            val action = OutfitListFragmentDirections.actionOutfitListFragmentToOutfitEditFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return outfitList.size
    }

    fun setData(outfit: List<Outfit>) {
        this.outfitList = outfit
        notifyDataSetChanged()
    }
}