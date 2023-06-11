package com.example.digikala.ui.fragments.category

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.digikala.R
import com.example.digikala.databinding.FragmentCategoryBinding
import com.example.digikala.util.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val viewModel: CategoryFragmentViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryFragmentRecyclerAdapter
    private var id = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        setUi()
        observer()
    }

    private fun setUi() {
        viewModel.getCategoryList()
        categoryAdapter = CategoryFragmentRecyclerAdapter {
            id = categoryAdapter.currentList[it].id
            navController.navigate(
                CategoryFragmentDirections.actionCategoryFragmentToProductsListFragment(
                    id
                )
            )
        }
        binding.recyclerCategory.adapter = categoryAdapter
    }

    private fun observer() {
        viewModel.categoryList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    hideProgressBar(binding.progressCategory)
                    response.data?.let {
                        categoryAdapter.submitList(it)
                    }
                }

                is Resources.Error -> {
                    hideProgressBar(binding.progressCategory)
                    response.message?.let {

                    }
                }

                is Resources.Loading -> {
                    showProgressBar(binding.progressCategory)
                }
            }

        }
    }
}

private fun showProgressBar(view: View) {
    view.visibility = View.VISIBLE
}

private fun hideProgressBar(view: View) {
    view.visibility = View.INVISIBLE
}