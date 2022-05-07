package com.example.allin.fragments.add

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.allin.R
import com.example.allin.model.OutfitClothingTable
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_add_clothing_to_outfits.view.*


class AddClothingToOutfits : Fragment() {

    private val args by navArgs<AddClothingToOutfitsArgs>()
    //This argument is carrying a value of Outfit(id, outfitName)
    private lateinit var addTopButton: Button
    private lateinit var addBotButton: Button
    private lateinit var addShoesButton: Button
    private lateinit var addOuterWearButton: Button


    private lateinit var mClosetViewModel: ClosetViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_clothing_to_outfits, container, false)
        //We have the argument of Outfit, Set it as the Outfit Title Name so the user, can set those values.
        //createOutfitInstructions()
        setHasOptionsMenu(true)

        //Takes the entered data
        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)

        //Displays Image
        view.outfit_title.setText(args.currentOutfit.outfitName)
        //Displays Top
        if (args.clothingTop?.image != null) {
            view.outfit_top_img.setImageURI(Uri.parse(args.clothingTop?.image))
        }
        //load bottoms image
        if (args.clothingBottom?.image != null) {
            view.outfit_bot_img.setImageURI(Uri.parse(args.clothingBottom?.image))
        }
        //load shoes image
        if(args.currentShoes?.image != null){
            view.outfit_shoes_img.setImageURI(Uri.parse(args.currentShoes?.image))
        }
        //load outerwear image
        if(args.currentOuterWear?.image != null){
            view.outfit_outerwear_img.setImageURI(Uri.parse(args.currentOuterWear?.image))
        }

        //Set the Clothing Tops Button
        addTopButton = view.outfit_add_top_btn
        addTopButton.setOnClickListener{
            //Navigate to the RecyclerView displaying only Clothing Tops
            findNavController().navigate(AddClothingToOutfitsDirections.actionAddClothingToOutfitsToClothingTopsList(args.currentOutfit,args.clothingTop,args.clothingBottom,args.currentShoes, args.currentOuterWear))
        }
        addBotButton = view.outfit_add_bot_btn
        addBotButton.setOnClickListener {
            findNavController().navigate(AddClothingToOutfitsDirections.actionAddClothingToOutfitsToClothingBotList(args.currentOutfit,args.clothingTop,args.clothingBottom,args.currentShoes, args.currentOuterWear))
        }

        addShoesButton = view.outfit_add_shoes_btn
        addShoesButton.setOnClickListener {
            findNavController().navigate(AddClothingToOutfitsDirections.actionAddClothingToOutfitsToClothingShoesList(args.currentOutfit,args.clothingTop,args.clothingBottom,args.currentShoes, args.currentOuterWear))
        }

        addOuterWearButton = view.outfit_add_outerwear_btn
        addOuterWearButton.setOnClickListener {
            findNavController().navigate(AddClothingToOutfitsDirections.actionAddClothingToOutfitsToClothingOuterWearList(args.currentOutfit,args.clothingTop,args.clothingBottom,args.currentShoes, args.currentOuterWear))
        }
        /**
         * After we Select Top we have a new Argument to pursue
         */
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_outfits_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // If item (Add button) is selected call InsertToDatabase()
        if (item.itemId == R.id.add_clothing_to_outfit_button) {
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {

        //Add logic before adding to Database
            val selectedDialog = AlertDialog.Builder(this.requireContext())
            selectedDialog.setPositiveButton("Yes") { _, _ ->
                //Add to the DB then return to main Outfit Page
                val outfit1 = args.currentOutfit.id
                val clothingTop = args.clothingTop?.clothingId
                val clothingBottom = args.clothingBottom?.clothingId
                val clothingShoes = args.currentShoes?.clothingId
                val clothingOuterWear = args.currentOuterWear?.clothingId

                if(outfit1 != null){
                    mClosetViewModel.addOutfitWithClothingMap(OutfitClothingTable(outfit1, clothingTop!!))
                }

                if(outfit1 != null){
                    mClosetViewModel.addOutfitWithClothingMap(OutfitClothingTable(outfit1, clothingBottom!!))
                }

                if(outfit1 != null){
                    mClosetViewModel.addOutfitWithClothingMap(OutfitClothingTable(outfit1, clothingShoes!!))
                }

                if(outfit1 != null){
                    mClosetViewModel.addOutfitWithClothingMap(OutfitClothingTable(outfit1, clothingOuterWear!!))
                }

                Toast.makeText(
                    requireContext(),
                    "Successfully created outfit.",
                    Toast.LENGTH_SHORT
                ).show()
                //Navigate Back
                findNavController().navigate(R.id.action_addClothingToOutfits_to_outfitListFragment)

            }
            selectedDialog.setNegativeButton("No") { _, _ -> }
            selectedDialog.setTitle("Create Outfit?")
            selectedDialog.create().show()
    }

    private fun createOutfitInstructions() {
        val outfitHelp = AlertDialog.Builder(requireContext())
        outfitHelp.setPositiveButton("Okay") { _, _ ->
            Toast.makeText(requireContext(), "Create Outfit!", Toast.LENGTH_SHORT).show()
        }
        outfitHelp.setNegativeButton("No") { _, _ ->
            // Once we have a delete button, well delete the Outfit from the DB if the user chooses not to create an Outfit.
            // deleteOutfit()
            //findNavController().navigate(R.id.action_addClothingToOutfits_to_outfitListFragment)
        }
        outfitHelp.setTitle("Create New Outfit?")
        outfitHelp.setMessage("Follow the Steps Below:\n" +
                "1. Click the Add Buttons of each clothing Type you wish to add.\n" +
                "2. Click Submit When You Are Ready\n")
        outfitHelp.create().show()
    }
}