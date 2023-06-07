package com.example.digikala.ui.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikala.data.models.products.ProductsResponse
import com.example.digikala.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _newestProduct = MutableLiveData<ProductsResponse>()
    val newestProduct: LiveData<ProductsResponse> = _newestProduct

    private val _mostVisitedProduct = MutableLiveData<ProductsResponse>()
    val mostVisitedProduct: LiveData<ProductsResponse> = _mostVisitedProduct

    private val _topRatedProduct = MutableLiveData<ProductsResponse>()
    val topRatedProduct: LiveData<ProductsResponse> = _topRatedProduct

    init {
        getNewestProducts()
        getMostVisitedProducts()
        getTopRatedProducts()
    }

    private fun getNewestProducts() {
        viewModelScope.launch {
            _newestProduct.postValue(repository.getNewestProducts(1, 10))
        }
    }

    private fun getMostVisitedProducts() {
        viewModelScope.launch {
            _mostVisitedProduct.postValue(repository.getMostVisitedProducts(1, 10))
        }
    }

    private fun getTopRatedProducts() {
        viewModelScope.launch {
            _topRatedProduct.postValue(repository.getTopRatedProducts(1, 10))
        }
    }
}