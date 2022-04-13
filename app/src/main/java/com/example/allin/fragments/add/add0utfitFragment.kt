package com.example.allin.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allin.R

/**
 * A simple [Fragment] subclass.
 * Use the [add0utfitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class add0utfitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add0utfit, container, false)
    }

}