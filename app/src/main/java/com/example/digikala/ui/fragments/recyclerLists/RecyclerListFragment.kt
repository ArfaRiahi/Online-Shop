package com.example.digikala.ui.fragments.recyclerLists

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikala.R
import com.example.digikala.databinding.FragmentRecyclerListBinding
import com.example.digikala.util.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {

    private lateinit var binding: FragmentRecyclerListBinding
    private val viewModel: RecyclerListViewModel by viewModels()
    private val args: RecyclerListFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private lateinit var recyclerAdapter: RecyclerListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        setUi()
        observer()
    }

    private fun setUi() {
        recyclerAdapter = RecyclerListAdapter {
            val id = recyclerAdapter.currentList[it].id
            navController.navigate(
                RecyclerListFragmentDirections.actionRecyclerListFragmentToDetailsFragment(id)
            )
        }
        binding.recyclerList.adapter = recyclerAdapter
    }

    private fun observer() {
        viewModel.getProductList(args.recyclerType)
        viewModel.productsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    hideProgressBar()
                    recyclerAdapter.submitList(response.data)
                }

                is Resources.Error -> {
                    hideProgressBar()
                    response.message?.let {

                    }
                }

                is Resources.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressRecyclerList.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressRecyclerList.visibility = View.VISIBLE
    }
}