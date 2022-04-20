package com.example.allin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Defines a Navigation Host, and creates a Bottom Nav bar.
        fun setupView(){
            var navController = findNavController(R.id.nav_frag_host)

            bot_nav_view.setupWithNavController(navController)

            setupActionBarWithNavController(navController)
        }

    }


}