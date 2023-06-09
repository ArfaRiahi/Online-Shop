package com.example.digikala.ui.fragments.pruductsList

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
class ProductListFragmentViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _productsList = MutableLiveData<ProductsResponse>()
    val productsList: LiveData<ProductsResponse> = _productsList

    fun getCProductList(productId: Int) {
        viewModelScope.launch {
            _productsList.postValue(repository.getProductsList(productId))
        }
    }
}