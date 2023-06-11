package com.example.digikala.ui.fragments.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikala.data.models.category.CategoryResponse
import com.example.digikala.data.repository.Repository
import com.example.digikala.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _categoryList = MutableLiveData<Resources<CategoryResponse>>()
    val categoryList: LiveData<Resources<CategoryResponse>> = _categoryList

    fun getCategoryList() {
        viewModelScope.launch {
            _categoryList.postValue(Resources.Loading())
            val response = repository.getCategoryLists()
            _categoryList.postValue(handleProductResponse(response))
        }
    }

    private fun handleProductResponse(response: Response<CategoryResponse>): Resources<CategoryResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }
}