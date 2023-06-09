package com.example.digikala.ui.fragments.pruductsList

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikala.R
import com.example.digikala.databinding.FragmentProductsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    private lateinit var binding: FragmentProductsListBinding
    private val viewModel: ProductListFragmentViewModel by viewModels()
    private val args: ProductsListFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private lateinit var productsAdapter: ProductListFragmentRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        setUi()
        observer()
    }

    private fun setUi() {
        productsAdapter = ProductListFragmentRecyclerAdapter {
            val id = productsAdapter.currentList[it].id
            navController.navigate(
                ProductsListFragmentDirections.actionProductsListFragmentToDetailsFragment(
                    id
                )
            )
        }
        binding.productListRecycler.adapter = productsAdapter
    }

    private fun observer() {
        viewModel.getCProductList(args.productId)
        viewModel.productsList.observe(viewLifecycleOwner) {
            productsAdapter.submitList(it)
        }
    }
}