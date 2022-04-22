package com.example.allin.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.allin.R
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFragment : Fragment() {

    // Each Argument is labeled by their KotlinClass name not the name provided in the nav_graph.xml
    // To find the name in nav_graph.xml, go to Fragment on graph > Attributes Window > Name
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mClothingViewModel: ClothingViewModel

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    private var dateReturned: Date = GregorianCalendar(year, month, day).time

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)

        val displayDate: Date = args.currentClothing.dateAdded
        toSimpleString(displayDate)

        view.updateType_editText.setText(args.currentClothing.type)
        view.updateColor.setText(args.currentClothing.color)
        view.updateStyle.setText(args.currentClothing.style)
        view.updateDescrip.setText(args.currentClothing.description)
        view.updateBrand_editText.setText(args.currentClothing.brand)
        view.updateTheme_editText.setText(args.currentClothing.theme)
        view.updateType_editText2.setText((args.currentClothing.image))

        view.update_dateAddedButton.text=(toSimpleString(displayDate))


        view.update_dateAddedButton.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireActivity(), DatePickerDialog.OnDateSetListener {
                        _: DatePicker, year: Int, month: Int, day: Int ->

                    dateReturned = GregorianCalendar(year, month, day).time
                    //Toast.makeText(requireContext(), "$dateReturned", Toast.LENGTH_SHORT).show()
                    update_dateAddedButton.text = toSimpleString(dateReturned)

                }, year, month, day
            )
            //This is necessary to display the calendar fragment on the current view.
            datePicker.show()
        }

        view.upButton.setOnClickListener() {
            updateItem()
        }

        //Add Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val type = updateType_editText.text.toString()
        val color = updateColor.text.toString()
        val style = updateStyle.text.toString()
        val descrip = updateDescrip.text.toString()
        val brand = updateBrand_editText.text.toString()
        val theme = updateTheme_editText.text.toString()
        val image2 = updateType_editText2.text.toString()



        if (inputCheck(type, color)) {
            //Create Clothing Object
            val updatedClothing = Clothing(args.currentClothing.id, type, color,style, descrip, dateReturned, brand, theme,image2)

            //Update Current Clothing Object
            mClothingViewModel.updateClothing(updatedClothing)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back to Clothing List screen
            findNavController().navigate(R.id.action_updateClothingFragment_to_clothingListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck (type: String, color: String): Boolean {
        return !(TextUtils.isEmpty(type) && TextUtils.isEmpty(color))
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
            // On Delete return back to Clothing List screen
            findNavController().navigate(R.id.action_updateClothingFragment_to_clothingListFragment)
        }
        builder.setNegativeButton("No") { _, _ ->}
        builder.setTitle("Delete ${args.currentClothing.type}?")
        builder.setMessage("Are you sure you want to delete ${args.currentClothing.type}?")
        builder.create().show()
    }

    // Only used to print the format of Date into a String for users to read.
    fun toSimpleString(date: Date?) = with(date ?: Date()){
        /**
         * This format can be changed. Use the link below to see the docs.
         * Scroll down to "Date and Time Pattern" Table
         * https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat
         */
        SimpleDateFormat("EEE, MMM d, yyyy").format(this)
    }
}