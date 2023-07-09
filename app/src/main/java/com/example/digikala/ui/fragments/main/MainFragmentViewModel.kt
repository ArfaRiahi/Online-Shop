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

    private val _progressInt = MutableLiveData<Int>()
    val progressInt: LiveData<Int> = _progressInt

    private val _newestProduct = MutableLiveData<Resources<ProductsResponse>>()
    val newestProduct: LiveData<Resources<ProductsResponse>> = _newestProduct

    private val _mostVisitedProduct = MutableLiveData<Resources<ProductsResponse>>()
    val mostVisitedProduct: LiveData<Resources<ProductsResponse>> = _mostVisitedProduct

    private val _topRatedProduct = MutableLiveData<Resources<ProductsResponse>>()
    val topRatedProduct: LiveData<Resources<ProductsResponse>> = _topRatedProduct

    private val _sliderProduct = MutableLiveData<Resources<ProductsResponse>>()
    val sliderProduct: LiveData<Resources<ProductsResponse>> = _sliderProduct

    private val _searchedProduct = MutableLiveData<Resources<ProductsResponse>>()
    val searchedProduct: LiveData<Resources<ProductsResponse>> = _searchedProduct

    private val _searchedProductPrice = MutableLiveData<Resources<ProductsResponse>>()
    val searchedProductPrice: LiveData<Resources<ProductsResponse>> = _searchedProductPrice

    private val _searchedSort = MutableLiveData<String>()
    val searchedSort: LiveData<String> = _searchedSort


    val newestProductsPage = 1
    val mostViewedPage = 1
    val topRatedPage = 1
    val searchedProductPage = 1

    init {
        getNewestProducts()
        getMostVisitedProducts()
        getTopRatedProducts()
        getSliderProducts()
        _progressInt.postValue(0)
    }

    fun increaseProgressInt() {
        val final = progressInt.value!!.plus(1)
        _progressInt.postValue(final)
    }

    fun setSearchesSort(sortType: String) {
        _searchedSort.value = sortType
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

    fun getSearchProduct(searchQuery: String, orderBy: String) {
        viewModelScope.launch {
            _searchedProduct.postValue(Resources.Loading())
            val response = repository.getSearchedProduct(searchQuery, orderBy)
            _searchedProduct.postValue(handleSearchResponse(response))
        }
    }

    fun getSearchProductPrice(searchQuery: String) {
        viewModelScope.launch {
            _searchedProductPrice.postValue(Resources.Loading())
            val response = repository.getSearchedProductPrice(searchQuery)
            _searchedProductPrice.postValue(handleSearchResponse(response))
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

    private fun handleSearchResponse(response: Response<ProductsResponse>): Resources<ProductsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }
}