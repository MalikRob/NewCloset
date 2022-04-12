package com.example.allin.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.allin.R
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    private lateinit var mClothingViewModel: ClothingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //Takes the entered data
        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)

        view.button.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    //Puts entered data into a variable
    private fun insertDataToDatabase() {
        val type = addType.text.toString()
        val color = addColor.text.toString()
        val description = addDescrip.text.toString()

        //Checks that the fields aren't empty
        if(inputCheck(type, color, description)){
            //Create Clothing Object
            val clothing = Clothing(0, type, color, description)

            //Add data to database
            mClothingViewModel.addClothing(clothing)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill Out All Fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck (type: String, color: String, description: String): Boolean {
        return !(TextUtils.isEmpty(type) && TextUtils.isEmpty(color) && TextUtils.isEmpty(description))
    }

}