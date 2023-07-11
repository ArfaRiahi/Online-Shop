package com.example.digikala.ui.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikala.R
import com.example.digikala.databinding.FragmentCartBinding
import com.example.digikala.util.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        setUi()
        observer()
    }

    private fun observer() {
        viewModel.itemID.observe(viewLifecycleOwner) { response ->when (response) {
                is Resources.Success -> {
                    response.data?.let {
                        adapter.submitList(it)
                    }
                }

                is Resources.Error -> {
                    response.message?.let {
                        Log.w("TAG", "observer: error", )
                    }
                }

                is Resources.Loading -> {
                    Log.w("TAG", "observer: loading", )
                }
            }
        }
    }

    private fun setUi() {
        adapter = CartRecyclerAdapter {

        }
        binding.recyclerCart.adapter = adapter
    }
}