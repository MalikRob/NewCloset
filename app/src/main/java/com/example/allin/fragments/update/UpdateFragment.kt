package com.example.allin.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.allin.R
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mClothingViewModel: ClothingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)

        view.updateType.setText(args.currentClothing.type)
        view.updateColor.setText(args.currentClothing.color)
        view.updateStyle.setText(args.currentClothing.style)
        view.updateDescrip.setText(args.currentClothing.description)

        view.upButton.setOnClickListener() {
            updateItem()
        }

        //Add Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val type = updateType.text.toString()
        val color = updateColor.text.toString()
        val style = updateStyle.text.toString()
        val descrip = updateDescrip.text.toString()

        if (inputCheck(type, color, style, descrip)) {
            //Create Clothing Object
            val updatedClothing = Clothing(args.currentClothing.id, type, color,style, descrip)
            //Update Current Clothing Object
            mClothingViewModel.updateClothing(updatedClothing)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck (type: String, color: String, style: String, description: String): Boolean {
        return !(TextUtils.isEmpty(type) && TextUtils.isEmpty(color) && TextUtils.isEmpty(description))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mClothingViewModel.deleteClothing(args.currentClothing)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentClothing.type}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->}
        builder.setTitle("Delete ${args.currentClothing.type}?")
        builder.setMessage("Are you sure you want to delete ${args.currentClothing.type}?")
        builder.create().show()
    }


}