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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allin.R
import com.example.allin.viewmodel.ClothingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_list.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var mClothingViewModel: ClothingViewModel
    var adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //RecyclerView
        //val adapter = ListAdapter()
        val recyclerView = view.clothing_recyclerview
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        //ClothingViewModel
        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)
        mClothingViewModel.readAllData.observe(viewLifecycleOwner, Observer { clothing ->
            adapter.setData(clothing)
        })

        //Button now takes the user to the Edit Clothing Page
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //Outfit Button takes the User to the altListFragment/Outfit List page
        view.floatingActionButton3.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_altListFragment)
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mClothingViewModel.deleteAllClothing()
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchDatabase(newText)
        }
        return true
    }
    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        mClothingViewModel.searchDatabase(searchQuery).observe(this
        ) { list ->
            list.let {
                adapter.setData(it)
            }
        }
    }

}



