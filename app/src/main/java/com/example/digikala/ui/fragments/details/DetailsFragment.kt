package com.example.digikala.ui.fragments.details

import android.content.ContentValues
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
import com.example.digikala.data.models.products.Image
import com.example.digikala.databinding.FragmentDetailsBinding
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsFragmentViewModel by viewModels()
    private lateinit var sliderView: SliderView
    private lateinit var navController: NavController
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var imgUrls: List<Image>
    private lateinit var sliderAdapter: SliderAdapterDetails

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        setUi()
    }

    private fun setUi() {
        val imagesUrl = ArrayList<String>()
        viewModel.getIdItemsProducts(args.detailsItem)
        viewModel.itemID.observe(viewLifecycleOwner) {
            with(binding) {
                Log.e(ContentValues.TAG, "args.detailItems: ${args.detailsItem}")
                tvDetailsName.text = it[0].name
                tvDetailsPrice.text = it[0].price + " Toman"
                tvDetailsDes.text = it[0].description
                val imagesNumber = it[0].images.size
                if (imagesNumber != 0) {
                    for (i in 0 until imagesNumber){
                        imagesUrl.add(it[0].images[i].src.toString())
                    }
                    sliderView = binding.sliderDetail
                    sliderAdapter = SliderAdapterDetails(imagesUrl)
                    sliderView.setSliderAdapter(sliderAdapter)
                }
            }
        }
    }
}