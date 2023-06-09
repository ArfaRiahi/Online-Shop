package com.example.digikala.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.digikala.R
import com.example.digikala.databinding.FragmentMainBinding
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var imageUrl: ArrayList<String>
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapterDashboard
    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var adapterNewest: MainRecyclersAdapter
    private lateinit var adapterMostVisited: MainRecyclersAdapter
    private lateinit var adapterTopRated: MainRecyclersAdapter
    private lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        navController = Navigation.findNavController(view)
        setUi()
        observer()
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
        setUpSliderView()
    }

    private fun createNewestAdapter() {
        adapterNewest = MainRecyclersAdapter(onClick = {
            navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
        })
        binding.recyclerNewest.adapter = adapterNewest
    }

    private fun createMostVisitedAdapter() {
        adapterMostVisited = MainRecyclersAdapter(onClick = {
            navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
        })
        binding.recyclerMostViewed.adapter = adapterMostVisited
    }

    private fun createTopRatedAdapter() {
        adapterTopRated = MainRecyclersAdapter(onClick = {
            navController.navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it))
        })
        binding.recyclerTopRated.adapter = adapterTopRated
    }

    private fun observer() {
        viewModel.newestProduct.observe(viewLifecycleOwner) {
            adapterNewest.submitList(it)
        }

        viewModel.mostVisitedProduct.observe(viewLifecycleOwner) {
            adapterMostVisited.submitList(it)
        }

        viewModel.topRatedProduct.observe(viewLifecycleOwner) {
            adapterTopRated.submitList(it)
        }
        viewModel.sliderProduct.observe(viewLifecycleOwner) {

            for (i in 0 until it.size) {
                imageUrl.add(it[i].images[0].src.toString())
                sliderAdapter.notifyDataSetChanged()
            }
        }
    }
}