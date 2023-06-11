package com.example.digikala.ui.fragments.details

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikala.R
import com.example.digikala.databinding.FragmentDetailsBinding
import com.example.digikala.util.Resources
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsFragmentViewModel by viewModels()
    private lateinit var sliderView: SliderView
    private lateinit var navController: NavController
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
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
            viewModel.itemID.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resources.Success -> {
                        //   hideProgressBar()
                        response.data?.let {
                            with(binding) {
                                tvDetailsName.text = it[0].name
                                tvDetailsPrice.text = " تومان " + it[0].price
                                tvDetailsDes.text =
                                    HtmlCompat.fromHtml(
                                        it[0].description.toString(),
                                        FROM_HTML_MODE_COMPACT
                                    )
                                val imagesNumber = it[0].images.size
                                if (imagesNumber != 0) {
                                    for (i in 0 until imagesNumber) {
                                        imagesUrl.add(it[0].images[i].src.toString())
                                    }
                                    sliderView = binding.sliderDetail
                                    sliderAdapter = SliderAdapterDetails(imagesUrl)
                                    sliderView.setSliderAdapter(sliderAdapter)
                                }
                            }
                        }
                    }

                    is Resources.Error -> {
                        //   hideProgressBar()
                        response.message?.let {

                        }
                    }

                    is Resources.Loading -> {
                        //      showProgressBar()
                    }
                }

            }
        }
    }
}