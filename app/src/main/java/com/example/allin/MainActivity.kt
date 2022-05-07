package com.example.allin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Defines a Navigation Host, and creates a Bottom Nav bar.
        val host : NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_frag_host) as NavHostFragment? ?: return

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)

        setupBottomNavMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bot_nav_view)
        bottomNav?.setupWithNavController(navController)

        // On pages AddFragment and Update Fragment, we don't want the user to go to Outfits page.
        // This should hide the Bottom nav view on these pages.
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.addClothingFragment || nd.id == R.id.updateClothingFragment || nd.id == R.id.addOutfitFragment
                || nd.id == R.id.addClothingToOutfits || nd.id == R.id.clothingTopsList || nd.id == R.id.clothingBotList
                || nd.id == R.id.clothingShoesList || nd.id == R.id.clothingOuterWearList) {
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }
        }
    }

    /*- FUNCTIONS REQUIRED TO INFLATE THE BOTTOM NAV MENU AND NAVIGATE WITH THE NAV GRAPH --------*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_frag_host))
                || super.onOptionsItemSelected(item)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_frag_host).navigateUp(appBarConfiguration)
    }
}