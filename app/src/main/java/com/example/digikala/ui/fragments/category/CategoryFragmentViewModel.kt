package com.example.digikala.ui.fragments.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikala.data.models.category.CategoryResponse
import com.example.digikala.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _categoryList = MutableLiveData<CategoryResponse>()
    val categoryList: LiveData<CategoryResponse> = _categoryList

    fun getCategoryList() {
        viewModelScope.launch {
            _categoryList.postValue(repository.getCategoryLists())
        }
    }
}