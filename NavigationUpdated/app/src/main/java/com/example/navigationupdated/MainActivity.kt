package com.example.navigationupdated

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private fun setupBottomNavMenu(navController: NavController){
        bottom_nav?.let{
            NavigationUI.setupWithNavController(it, navController)
        }
    }
    private fun setupSideNavigationMenu(navController: NavController){
        nav_view?.let{
            NavigationUI.setupWithNavController(it, navController)
        }
    }
    private fun setupActionBar(navController: NavController){
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }
}
