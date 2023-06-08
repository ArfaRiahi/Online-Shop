package com.example.digikala.ui.fragments.details

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
class DetailsFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _itemID = MutableLiveData<ProductsResponse>()
    val itemID: LiveData<ProductsResponse> = _itemID

    fun getIdItemsProducts(id: Int) {
        viewModelScope.launch {
            _itemID.postValue(repository.getIdItemsProducts(id))
        }
    }
}