package com.edifica.activities.clients

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityClientMain : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_client)

        val navController: NavController = findNavController(R.id.nav_client_fragment)

        NavigationUI.setupWithNavController(navView, navController)
    }
}