package com.example.digikala.ui.fragments.search

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
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _searchedProduct = MutableLiveData<Resources<ProductsResponse>>()
    val searchedProduct: LiveData<Resources<ProductsResponse>> = _searchedProduct

    private val _searchedProductPrice = MutableLiveData<Resources<ProductsResponse>>()
    val searchedProductPrice: LiveData<Resources<ProductsResponse>> = _searchedProductPrice

    private val _searchedSort = MutableLiveData<String>()
    val searchedSort: LiveData<String> = _searchedSort

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

    private fun handleSearchResponse(response: Response<ProductsResponse>): Resources<ProductsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    fun setSearchesSort(sortType: String) {
        _searchedSort.value = sortType
    }
}