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
import com.example.allin.model.Outfit
import com.example.allin.viewmodel.ClosetViewModel
import kotlinx.android.synthetic.main.fragment_add_outfit.*

/**
 */
class AddOutfitFragment : Fragment() {

    //Define any buttons you have here, to help keep track of them.
    private lateinit var outfitName: EditText
    private lateinit var addOutfitBtn: Button

    private lateinit var mClosetViewModel: ClosetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_outfit, container, false)

        outfitDirections()

        mClosetViewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)

        outfitName = view.findViewById(R.id.outfit_name_et)
        addOutfitBtn = view.findViewById(R.id.add_new_outfit_btn)

        addOutfitBtn.setOnClickListener {
            val string: String = outfitName.text.toString()
            if((string.isNotEmpty())) {
                Toast.makeText(requireContext(), "You must enter a new Outfit Name", Toast.LENGTH_SHORT).show()
                val name = outfit_name_et.text.toString()
                val outfit = Outfit(0, name)

                mClosetViewModel.addOutfit(outfit)

                findNavController().navigate(AddOutfitFragmentDirections.actionAddOutfitFragmentToAddClothingToOutfits(outfit, null, null, null, null))
                //findNavController().navigate(R.id.action_addOutfitFragment_to_addClothingToOutfits)
            } else {
                Toast.makeText(requireContext(), "Please enter an outfit name", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }


    private fun outfitDirections() {
        val outfitHelp = AlertDialog.Builder(requireContext())
        outfitHelp.setPositiveButton("Create") { _, _ ->
            Toast.makeText(requireContext(), "Create Outfit!", Toast.LENGTH_SHORT).show()
        }
        //outfitHelp.setNegativeButton("No") { _, _ -> findNavController().navigate(R.id.action_addOutfitFragment_to_outfitListFragment)}
        outfitHelp.setTitle("Create New Outfit?")
        outfitHelp.setMessage("Follow the Steps Below:\n" +
                "1. Enter A Name For Your New Outfit\n" +
                "2. Click Submit When You Are Ready\n")
        outfitHelp.create().show()
    }
}