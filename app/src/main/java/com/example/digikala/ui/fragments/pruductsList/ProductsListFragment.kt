package com.example.digikala.ui.fragments.pruductsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.digikala.databinding.FragmentProductsListBinding

class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductsListBinding.inflate(layoutInflater)
        return binding.root
    }
}