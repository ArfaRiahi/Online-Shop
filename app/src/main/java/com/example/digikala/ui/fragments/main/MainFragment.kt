package com.example.digikala.ui.fragments.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.digikala.R
import com.example.digikala.databinding.FragmentMainBinding
import com.example.digikala.util.Resources
import com.example.loadinganimation.LoadingAnimation
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var imageUrl: ArrayList<String>
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapterDashboard
    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var adapterNewest: MainRecyclersAdapter
    private lateinit var headerAdapter: RecyclerHeaderAdapter
    private lateinit var adapterMostVisited: MainRecyclersAdapter
    private lateinit var adapterTopRated: MainRecyclersAdapter
    private lateinit var adapterSearch: RecyclerSearchAdapter
    private lateinit var navController: NavController
    private lateinit var sortType: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        navController = findNavController()
        observer()
        setUi()
        var job: Job? = null
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    this.let {
                        if (newText.toString().isNotEmpty()) {
                            binding.recyclerSearchResult.visibility = View.VISIBLE
                            binding.dashboardRadioGroup.visibility = View.VISIBLE
                            sortType =
                                getSearchSort(binding.dashboardRadioGroup.checkedRadioButtonId)
                            if (sortType == "d") {
                                viewModel.getSearchProductPrice(newText.toString())
                            } else {
                                viewModel.getSearchProduct(newText.toString(), sortType)
                            }
                            binding.dashboardRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                                viewModel.setSearchesSort(getSearchSort(checkedId))
                                sortType =
                                    getSearchSort(binding.dashboardRadioGroup.checkedRadioButtonId)
                                if (sortType == "d") {
                                    viewModel.getSearchProductPrice(newText.toString())
                                } else {
                                    viewModel.getSearchProduct(
                                        newText.toString(),
                                        viewModel.searchedSort.value.toString()
                                    )
                                }
                            }
                        } else if (newText.toString().isEmpty()) {
                            job?.cancel()
                            binding.recyclerSearchResult.visibility = View.GONE
                            binding.dashboardRadioGroup.visibility = View.GONE
                        }
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

        })
    }

    private fun setUpSliderView() {
        imageUrl = ArrayList()
        sliderView = binding.slider
        sliderAdapter = SliderAdapterDashboard(imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 2
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    private fun setUi() {
        createNewestAdapter()
        createMostVisitedAdapter()
        createTopRatedAdapter()
        createSearchAdapter()
        setUpSliderView()
    }

    private fun createSearchAdapter() {
        adapterSearch = RecyclerSearchAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
            } else {
                noNetToast(requireContext())
            }
        })
        binding.recyclerSearchResult.adapter = adapterSearch
    }

    private fun createNewestAdapter() {
        headerAdapter = RecyclerHeaderAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(
                    MainFragmentDirections.actionMainFragmentToRecyclerListFragment(
                        "newest"
                    )
                )
            } else {
                noNetToast(requireContext())
            }
        })
        adapterNewest = MainRecyclersAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
            } else {
                noNetToast(requireContext())
            }
        })
        val concatAdapter = ConcatAdapter(headerAdapter, adapterNewest)
        binding.recyclerNewest.adapter = concatAdapter
    }

    private fun createMostVisitedAdapter() {
        headerAdapter = RecyclerHeaderAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(
                    MainFragmentDirections.actionMainFragmentToRecyclerListFragment(
                        "most"
                    )
                )
            } else {
                noNetToast(requireContext())
            }
        })
        adapterMostVisited = MainRecyclersAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
            } else {
                noNetToast(requireContext())
            }
        })
        val concatAdapter = ConcatAdapter(headerAdapter, adapterMostVisited)
        binding.recyclerMostViewed.adapter = concatAdapter
    }

    private fun createTopRatedAdapter() {
        headerAdapter = RecyclerHeaderAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(
                    MainFragmentDirections.actionMainFragmentToRecyclerListFragment(
                        "top"
                    )
                )
            } else {
                noNetToast(requireContext())
            }
        })
        adapterTopRated = MainRecyclersAdapter(onClick = {
            if (isOnline(requireContext())) {
                navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
            } else {
                noNetToast(requireContext())
            }
        })
        val concatAdapter = ConcatAdapter(headerAdapter, adapterTopRated)
        binding.recyclerTopRated.adapter = concatAdapter
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.progressInt.observe(viewLifecycleOwner) {
                    if (it == 3) hideProgressBar(binding.progressDashboard, binding.nestedScroll)
                }
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
                                binding.progressSearch.visibility = View.INVISIBLE
                                binding.categorySearch.visibility = View.VISIBLE
                                binding.recyclerSearchResult.visibility = View.VISIBLE
                                adapterSearch.submitList(it)
                            }
                        }

                        is Resources.Loading -> {
                            binding.progressSearch.visibility = View.VISIBLE
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
                                binding.progressSearch.visibility = View.INVISIBLE
                                binding.categorySearch.visibility = View.VISIBLE
                                binding.recyclerSearchResult.visibility = View.VISIBLE
                                adapterSearch.submitList(it)
                            }
                        }

                        is Resources.Loading -> {
                            binding.progressSearch.visibility = View.VISIBLE
                        }
                    }
                }
                viewModel.newestProduct.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resources.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "اتصال اینترنت را چک کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resources.Success -> {
                            viewModel.increaseProgressInt()
                            response.data?.let {
                                adapterNewest.submitList(it)
                            }
                        }

                        is Resources.Loading -> {
                            showProgressBar(binding.progressDashboard, binding.nestedScroll)
                        }
                    }
                }
                viewModel.mostVisitedProduct.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resources.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "اتصال اینترنت را چک کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resources.Success -> {
                            viewModel.increaseProgressInt()
                            response.data?.let {
                                adapterMostVisited.submitList(it)
                            }
                        }

                        is Resources.Loading -> {
                            showProgressBar(binding.progressDashboard, binding.nestedScroll)
                        }
                    }
                }
                viewModel.topRatedProduct.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resources.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "اتصال اینترنت را چک کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resources.Success -> {
                            viewModel.increaseProgressInt()
                            response.data?.let {
                                adapterTopRated.submitList(it)
                            }
                        }

                        is Resources.Loading -> {
                            showProgressBar(binding.progressDashboard, binding.nestedScroll)
                        }
                    }
                }
                viewModel.sliderProduct.observe(viewLifecycleOwner) { response ->
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
                            viewModel.increaseProgressInt()
                            response.data?.let {
                                for (i in 0 until it.size) {
                                    imageUrl.add(it[i].images[0].src.toString())
                                    sliderAdapter.notifyDataSetChanged()
                                }
                            }
                        }

                        is Resources.Loading -> {
                            showProgressBar(binding.progressDashboard, binding.nestedScroll)
                        }
                    }
                }
            }
        }
    }
}

private fun hideProgressBar(view: View, backLayout: NestedScrollView) {
    backLayout.visibility = View.VISIBLE
    view as LoadingAnimation
    view.visibility = View.INVISIBLE
}

private fun showProgressBar(view: View, backLayout: NestedScrollView) {
    val loadingAnim = view as LoadingAnimation
    backLayout.visibility = View.INVISIBLE
    loadingAnim.visibility = View.VISIBLE
    loadingAnim.setEnlarge(5)
}

private fun getSearchSort(id: Int): String {
    return when (id) {
        R.id.radio_newest -> "date"
        R.id.radio_top_sell -> "popularity"
        R.id.radio_price_asc -> "price"
        R.id.radio_price_des -> "d"
        else -> ""
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return capabilities != null
}

private fun noNetToast(context: Context) {
    Toast.makeText(context, "اتصال اینترنت را چک کنید", Toast.LENGTH_SHORT).show()
}