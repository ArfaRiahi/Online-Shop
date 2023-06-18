package com.example.digikala.ui.fragments.cart

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
class CartViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _itemID = MutableLiveData<Resources<ProductsResponse>>()
    val itemID: LiveData<Resources<ProductsResponse>> = _itemID

    fun getIdItemsProducts(id: Int) {
        viewModelScope.launch {
            _itemID.postValue(Resources.Loading())
            val response = repository.getIdItemsProducts(id)
            _itemID.postValue(handleProductResponse(response))
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