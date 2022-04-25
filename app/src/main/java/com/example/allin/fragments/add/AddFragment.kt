package com.example.allin.fragments.add

import android.Manifest.permission.CAMERA
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.allin.R
import com.example.allin.model.Clothing
import com.example.allin.viewmodel.ClothingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    private lateinit var mClothingViewModel: ClothingViewModel
    lateinit var currentPhotoPath: String
    private val REQUEST_IMAGE_CAPTURE = 1
    private val PICK_IMAGE = 2
    private val REQUEST_PERMISSION = 100
    private lateinit var photoView: ImageView
    private lateinit var imageGallery: ImageButton
    private lateinit var captureImage: ImageButton
    var uri: Uri? = null


    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    private var dateReturned = GregorianCalendar(year, month, day).time

    // this is the permissions
    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(CAMERA), REQUEST_PERMISSION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        photoView = view.findViewById(R.id.clothing_item_photo) as ImageView
        imageGallery = view.findViewById(R.id.image_gallery_button) as ImageButton
        captureImage = view.findViewById(R.id.camera_button) as ImageButton



        //Takes the entered data
        mClothingViewModel = ViewModelProvider(this).get(ClothingViewModel::class.java)

        //Add Button on Clothing Edit Screen
        view.add_button.setOnClickListener{
            insertDataToDatabase()
        }
  //these are the camera action buttons when on clicked what they are doing starts to run
        captureImage.apply {
            val packageManager: PackageManager = requireActivity().packageManager

            setOnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
                    intent.resolveActivity(packageManager)?.also {
                        val photoFile: File? = try {
                            createCapturedPhoto()
                        } catch (ex: IOException) {
                            // If there is error while creating the File, it will be null
                            null
                        }
                        photoFile?.also {
                            val photoURI = FileProvider.getUriForFile(requireActivity(), "com.example.allin.provider", it )
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                        }

                    }
                }
            }
        }
        imageGallery.apply {
            val packageManager: PackageManager = requireActivity().packageManager

            val pickImage = Intent (Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            val resolvedActivity: ResolveInfo? = packageManager.resolveActivity(pickImage,PackageManager.MATCH_DEFAULT_ONLY)

            if (resolvedActivity == null){
                isEnabled = false
            }
            //New Addition
            setOnClickListener {
                Intent(Intent.ACTION_OPEN_DOCUMENT).also { intent ->
                    intent.type = "image/*"
                }

                startActivityForResult(pickImage, PICK_IMAGE)
            }
        }


        val spinner: Spinner = view.findViewById(R.id.clothingType_spinner)
        val clothingType = resources.getStringArray(R.array.Clothing_type)
        val clothingType_adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.Clothing_type,
            android.R.layout.simple_spinner_item
        )
        spinner.adapter = clothingType_adapter
        val spinner2: Spinner = view.clothingStyle_spinner
        val topstyle = resources.getStringArray(R.array.top_style)
        var styleChild_adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.top_style,
            android.R.layout.simple_spinner_item
        )
        spinner2.adapter = styleChild_adapter
        val spinner3: Spinner = view.clothingColor_Spinner
        val color = resources.getStringArray(R.array.colors)
        val color_adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.colors,
            android.R.layout.simple_spinner_item
        )
        spinner3.adapter = color_adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //Toast.makeText(requireContext(), clothingType[position], Toast.LENGTH_SHORT).show()
                when (position) {
                    0 -> {
                        styleChild_adapter = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.top_style,
                            android.R.layout.simple_spinner_item
                        )
                        spinner2.adapter = styleChild_adapter
                    }
                    1 -> {
                        styleChild_adapter = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.bottom_style,
                            android.R.layout.simple_spinner_item
                        )
                        spinner2.adapter = styleChild_adapter
                    }
                    2 -> {
                        styleChild_adapter = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.shoes_style,
                            android.R.layout.simple_spinner_item
                        )
                        spinner2.adapter = styleChild_adapter
                    }
                    3 -> {
                        styleChild_adapter = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.outerwear_style,
                            android.R.layout.simple_spinner_item
                        )
                        spinner2.adapter = styleChild_adapter
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        spinner3.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

               val colorEntry = parent.getItemAtPosition(position).toString()
                //Toast.makeText(requireContext(), color[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //No action determined yet for no selection.
            }
        }

        /**
         * Spinner for Brand options
         */
        val brandSpinner: Spinner = view.clothingBrand_spinner
        val brand = resources.getStringArray(R.array.brands)
        val brandAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.brands,
            android.R.layout.simple_spinner_item
        )
        brandSpinner.adapter = brandAdapter

        brandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val brandEntry = parent.getItemAtPosition(position).toString()
                //Toast.makeText(requireContext(), brand[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //No action determined yet for no selection.
            }
        }

        /**
         * Spinner for Theme options
         */
        val themeSpinner: Spinner = view.clothingTheme_spinner
        val themes = resources.getStringArray(R.array.themes)
        val themeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.themes,
            android.R.layout.simple_spinner_item
        )
        themeSpinner.adapter = themeAdapter

        themeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val themeEntry = parent.getItemAtPosition(position).toString()
                //Toast.makeText(requireContext(), themes[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        /**
         * Choose Date Added Button
         */
        //DatePicker Fragment called from the button labeled on the XML file.
        view.dateAdded_button.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireActivity(), DatePickerDialog.OnDateSetListener {
                        _: DatePicker, year: Int, month: Int, day: Int ->

                    dateReturned = GregorianCalendar(year, month, day).time
                    //Toast.makeText(requireContext(), "$dateReturned", Toast.LENGTH_SHORT).show()
                    dateAdded_button.text = toSimpleString(dateReturned)

                }, year, month, day
            )
            //This is necessary to display the calendar fragment on the current view.
            datePicker.show()
        }
        return view
    }
    // this is the activity for running the camera or gallery action to set the photo inside imageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                uri = Uri.parse(currentPhotoPath)
                clothing_item_photo.setImageURI(uri)
            }
            else if (requestCode == PICK_IMAGE) {
                uri = data?.getData()
                clothing_item_photo.setImageURI(uri)

            }
        }
    }

    //Puts entered data into a variable
    private fun insertDataToDatabase() {
        val type = clothingType_spinner.selectedItem.toString()
        val color = clothingColor_Spinner.selectedItem.toString()
        val style = clothingStyle_spinner.selectedItem.toString()
        val brand = clothingBrand_spinner.selectedItem.toString()
        val theme = clothingTheme_spinner.selectedItem.toString()
        // the value to store the image uri as a string into the database
        val imageString = uri.toString()
        val description = outfit_name_edittext.text.toString()


        //Checks that the fields aren't empty
        if(inputCheck(type, color)){
            //Create Clothing Object
            val clothing = Clothing(0, type, color, style, description, dateReturned, brand, theme, imageString)

            //Add data to database
            mClothingViewModel.addClothing(clothing)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addClothingFragment_to_clothingListFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill Out All Fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck (type: String, color: String): Boolean {
        return !(TextUtils.isEmpty(type) && TextUtils.isEmpty(color))
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

    // this is the function to create the path file for the image that gets called in camera
    @Throws(IOException::class)
    private fun createCapturedPhoto(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_${timestamp}",".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }


}

