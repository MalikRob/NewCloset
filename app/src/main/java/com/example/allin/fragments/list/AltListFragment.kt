package com.example.allin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allin.R
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.fragment_alt_list.view.*

class AltListFragment : Fragment() {

    private lateinit var mClothingViewModel: ClothingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_alt_list, container, false)

        //RecyclerView
        val adapter = AltListAdapter()
        val recyclerView = view.recyclerviewO
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ClothingViewModel
        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)
        mClothingViewModel.readAllData.observe(viewLifecycleOwner, Observer { clothing ->
            adapter.setData(clothing)
        })

        //Arrow Button now takes the user back to the Home screen
        view.homeButton.setOnClickListener{
            findNavController().navigate(R.id.action_altListFragment_to_listFragment)
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
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mClothingViewModel.deleteAllClothing()
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