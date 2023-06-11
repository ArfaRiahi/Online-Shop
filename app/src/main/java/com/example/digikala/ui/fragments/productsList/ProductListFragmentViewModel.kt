package com.example.digikala.ui.fragments.productsList

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
class ProductListFragmentViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _productsList = MutableLiveData<Resources<ProductsResponse>>()
    val productsList: LiveData<Resources<ProductsResponse>> = _productsList

    fun getCProductList(productId: Int) {
        viewModelScope.launch {
            _productsList.postValue(Resources.Loading())
            val response = repository.getProductsList(productId)
            _productsList.postValue(handleProductResponse(response))
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