package com.example.allin.fragments.add

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.allin.R
import com.example.allin.model.Outfit
import kotlinx.android.synthetic.main.fragment_add_clothing_to_outfits.view.*


class AddClothingToOutfits : Fragment() {

    //This argument is carrying a value of Outfit(id, outfitName)
    private val args by navArgs<AddClothingToOutfitsArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_clothing_to_outfits, container, false)

        //We have the argument of Outfit, Set it as the Outfit Title Name so the user, can set those values.
        view.outfit_title.text = args.currentOutfit.outfitName

        createOutfitInstructions()



        return view
    }

    private fun createOutfitInstructions() {
        val outfitHelp = AlertDialog.Builder(requireContext())
        outfitHelp.setPositiveButton("Okay") { _, _ ->
            Toast.makeText(requireContext(), "Create Outfit!", Toast.LENGTH_SHORT).show()
        }
        outfitHelp.setNegativeButton("No") { _, _ ->
            // Once we have a delete button, well delete the Outfit from the DB if the user chooses not to create an Outfit.
            // deleteOutfit()
            findNavController().navigate(R.id.action_addClothingToOutfits_to_outfitListFragment)
        }
        outfitHelp.setTitle("Create New Outfit?")
        outfitHelp.setMessage("Follow the Steps Below:\n" +
                "1. Click the Add Buttons of each clothing Type you wish to add.\n" +
                "2. Click Submit When You Are Ready\n")
        outfitHelp.create().show()
    }

}