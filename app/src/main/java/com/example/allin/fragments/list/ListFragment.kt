package com.example.allin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allin.R
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_clothing_list.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var mClosetViewModel: ClosetViewModel
    var adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clothing_list, container, false)
        //Clear Stack of Navigations

        //RecyclerView
        //val adapter = ListAdapter()
        val recyclerView = view.clothing_recyclerview
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        //ClothingViewModel
        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        mClosetViewModel.readAllClothingData.observe(viewLifecycleOwner, Observer { clothing ->
            adapter.setData(clothing)
        })

        //Button now takes the user to the Edit Clothing Page
        view.floatingActionButton.setOnClickListener {
            //<!-- Action goes from Clothing List to $AddFragment.kt
            findNavController().navigate(R.id.action_clothingListFragment_to_addClothingFragment)
        }

        //Add menu, See Apps Top Bar Menu Functions for details.
        setHasOptionsMenu(true)

        return view
    }

    /**
     * Apps Top Bar Menu is handled below.
     */

    // Creates the View for the Menu. Two buttons Search and Delete currently.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        // Modifies Keyboard Menu Options.
        searchView?.imeOptions
        // Creates a Submit Button on Search Menu NOT THE KEYBOARD ONE
        searchView?.isSubmitButtonEnabled = true

        //This allows user to search Query from Database
        searchView?.setOnQueryTextListener(this)
    }

    // When User selects the Delete Button it makes a call to the DB DELETE Query
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    //When Query Submit button is pressed. Changes are confirmed
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return false
    }
    // Search Menu also changes live while searching DB.
    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchDatabase(newText)
        }
        return true
    }

    // User Input is passed in as parameter to search DB.
    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"
        // See ClothingDao for SQLite Query "fun searchDatabase(searchQuery: String)"
        mClosetViewModel.searchDatabase(searchQuery).observe(this
        ) { list ->
            list.let {
                adapter.setData(it)
            }
        }
    }

    // Deletes Everything From the list. Prompts the user to make changes to Delete.
    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mClosetViewModel.deleteAllClothing()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything.",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}




