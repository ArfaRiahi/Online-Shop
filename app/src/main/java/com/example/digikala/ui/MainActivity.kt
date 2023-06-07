package com.example.digikala.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.digikala.R
import com.example.digikala.databinding.ActivityMainBinding
import com.example.digikala.ui.fragments.category.CategoryFragment
import com.example.digikala.ui.fragments.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentStatusBar()
        loadFragment(MainFragment())

        //bottom navigation setup
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    loadFragment(MainFragment())
                    true
                }

                R.id.categoryFragment -> {
                    loadFragment(CategoryFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainerView.id, fragment)
        transaction.commit()
    }

    private fun transparentStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}