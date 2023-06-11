package com.example.digikala.ui.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikala.data.models.products.ProductsResponse
import com.example.digikala.data.repository.Repository
import com.example.digikala.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _newestProduct = MutableLiveData<Resources<ProductsResponse>>()
    val newestProduct: LiveData<Resources<ProductsResponse>> = _newestProduct

    private val _mostVisitedProduct = MutableLiveData<Resources<ProductsResponse>>()
    val mostVisitedProduct: LiveData<Resources<ProductsResponse>> = _mostVisitedProduct

    private val _topRatedProduct = MutableLiveData<Resources<ProductsResponse>>()
    val topRatedProduct: LiveData<Resources<ProductsResponse>> = _topRatedProduct

    private val _sliderProduct = MutableLiveData<Resources<ProductsResponse>>()
    val sliderProduct: LiveData<Resources<ProductsResponse>> = _sliderProduct

    val newestProductsPage = 1
    val mostViewedPage = 1
    val topRatedPage = 1

    init {
        getNewestProducts()
        getMostVisitedProducts()
        getTopRatedProducts()
        getSliderProducts()
    }

    private fun getNewestProducts() {
        viewModelScope.launch {
            _newestProduct.postValue(Resources.Loading())
            val response = repository.getNewestProducts(newestProductsPage, 10)
            _newestProduct.postValue(handleProductResponse(response))
        }
    }

    private fun getMostVisitedProducts() {
        viewModelScope.launch {
            _mostVisitedProduct.postValue(Resources.Loading())
            val response = repository.getMostVisitedProducts(mostViewedPage, 10)
            _mostVisitedProduct.postValue(handleProductResponse(response))
        }
    }

    private fun getTopRatedProducts() {
        viewModelScope.launch {
            _topRatedProduct.postValue(Resources.Loading())
            val response = repository.getTopRatedProducts(topRatedPage, 10)
            _topRatedProduct.postValue(handleProductResponse(response))
        }
    }

    private fun getSliderProducts() {
        viewModelScope.launch {
            _sliderProduct.postValue(Resources.Loading())
            val response = repository.getSliderProducts()
            _sliderProduct.postValue(handleProductResponse(response))
        }
    }

    private fun handleProductResponse(response: Response<ProductsResponse>): Resources<ProductsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

}