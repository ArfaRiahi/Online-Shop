package com.example.digikala.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.digikala.R
import com.example.digikala.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        transparentStatusBar()
        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun transparentStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}