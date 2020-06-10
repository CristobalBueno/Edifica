package com.edifica.activities.business

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityBusinessMain : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_business)

        val navController: NavController = findNavController(R.id.nav_business_fragment)

        NavigationUI.setupWithNavController(navView, navController)
    }
}
