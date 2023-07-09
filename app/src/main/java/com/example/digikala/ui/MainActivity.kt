package com.example.digikala.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.digikala.R
import com.example.digikala.databinding.ActivityMainBinding
import com.example.digikala.databinding.LayoutNoInternetBinding
import com.example.digikala.util.observeconnectivity.NetworkStatus
import com.example.digikala.util.observeconnectivity.NetworkStatusHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingInternetBinding: LayoutNoInternetBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isOnline(this)) binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        else {
            bindingInternetBinding =
                DataBindingUtil.setContentView(this, R.layout.layout_no_internet)
            bindingInternetBinding.btnNoNet.setOnClickListener {
                if (isOnline(this)) {
                    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
                    transparentStatusBar()
                    setBottomNavigation()
                } else {
                    Toast.makeText(this, "اتصال اینترنت برقرار نشد", Toast.LENGTH_SHORT).show()
                }
            }
            return
        }

        NetworkStatusHelper(this@MainActivity).observe(this) {
            when (it) {
                NetworkStatus.Available -> Toast.makeText(this, "Network OK!", Toast.LENGTH_LONG)
                    .show()

                NetworkStatus.Unavailable -> {
                }
            }
        }
        transparentStatusBar()
        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun transparentStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}