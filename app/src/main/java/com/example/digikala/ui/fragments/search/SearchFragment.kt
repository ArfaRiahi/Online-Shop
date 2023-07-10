package com.example.digikala.ui.fragments.search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikala.R
import com.example.digikala.databinding.FragmentSearchBinding
import com.example.digikala.ui.fragments.main.RecyclerSearchAdapter
import com.example.digikala.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var sortType: String
    private val args: SearchFragmentArgs by navArgs()
    private lateinit var adapterSearch: RecyclerSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        navController = findNavController()
        createSearchAdapter()
        observe()
        viewModel.getSearchProductPrice(args.searchedWord)
    }

    private fun createSearchAdapter() {
        adapterSearch = RecyclerSearchAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                        it
                    )
                )
            } else {
                noNetToast(requireContext())
            }
        })
        binding.searchFragmentRecycler.adapter = adapterSearch
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchedProductPrice.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resources.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "اتصال اینترنت را چک کنید",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        is Resources.Success -> {
                            response.data?.let {
                                adapterSearch.submitList(it)
                                binding.progressSearchFragment.visibility = View.INVISIBLE
                                binding.searchFragmentRecycler.visibility = View.VISIBLE
                            }
                            Toast.makeText(
                                requireContext(),
                                "کالای مورد نظر یافت نشد",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resources.Loading -> {
                            binding.progressSearchFragment.visibility = View.VISIBLE
                            binding.searchFragmentRecycler.visibility = View.INVISIBLE
                        }
                    }
                }
                viewModel.searchedProduct.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resources.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "اتصال اینترنت را چک کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resources.Success -> {
                            response.data?.let {
                                adapterSearch.submitList(it)
                            }
                            Toast.makeText(
                                requireContext(),
                                "کالای مورد نظر یافت نشد",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resources.Loading -> {
                        }
                    }
                }
            }
        }
    }
}

private fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return capabilities != null
}

private fun noNetToast(context: Context) {
    Toast.makeText(context, "اتصال اینترنت را چک کنید", Toast.LENGTH_SHORT).show()
}