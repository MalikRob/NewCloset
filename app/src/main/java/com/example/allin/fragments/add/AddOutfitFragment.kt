package com.example.allin.fragments.add

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.allin.R
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.fragment_add_outfit.*
import kotlinx.android.synthetic.main.fragment_add_outfit.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddOutfitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddOutfitFragment : Fragment() {

    //Define any buttons you have here, to help keep track of them.

    private lateinit var mClothingViewModel: ClothingViewModel

    private lateinit var addOutfitTop: Button
    private lateinit var addOutfitBottom: Button
    private lateinit var addOutfitName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set the view, allow options menu.
        val view = inflater.inflate(R.layout.fragment_add_outfit, container, false)
        setHasOptionsMenu(true)
        //As a new user enters the page, the directions will prompt them how to create an outfit.
        outfitDirections()
        /**
         *************** BELOW HERE WE ADD THE BUTTONS TO THE VIEW  ******************
         */

        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)
        addOutfitTop = view.findViewById(R.id.outfit_add_top_btn)
        addOutfitBottom = view.findViewById(R.id.outfit_add_bot_btn)
        addOutfitName = view.findViewById(R.id.outfit_name_et)

        //Set a listener for the Add Outfit Recyclerview.
        addOutfitTop.setOnClickListener {
            //When user clicks the button to add a new outfit, they'll be brought to a new page.
            findNavController().navigate(R.id.action_addOutfitFragment_to_clothingTopsList)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Call the menu you want to use
        inflater.inflate(R.menu.add_outfits_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // If item (Add button) is selected call InsertToDatabase()
        if (item.itemId == R.id.add_clothing_button) {
            insertNewOutfit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertNewOutfit() {
        val outfitName = outfit_name_et.text.toString()
    }

    private fun outfitDirections() {
        val outfitHelp = AlertDialog.Builder(requireContext())
        outfitHelp.setPositiveButton("Create") { _, _ ->
            Toast.makeText(requireContext(), "Create Outfit!", Toast.LENGTH_SHORT).show()
        }
        outfitHelp.setNegativeButton("No") { _, _ -> findNavController().navigate(R.id.action_addOutfitFragment_to_outfitListFragment)}
        outfitHelp.setTitle("Create New Outfit?")
        outfitHelp.setMessage("Follow the Steps Below:\n" +
                "1. Add new outfit name\n" +
                "2. Choose Outfit Top\n" +
                "3. Click + button in upper Right corner\n")

        outfitHelp.create().show()
    }

}