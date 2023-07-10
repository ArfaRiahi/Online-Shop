package com.example.digikala.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digikala.data.models.customer.CustomerDto
import com.example.digikala.data.models.order.Billing
import com.example.digikala.data.models.order.OrderDto
import com.example.digikala.data.repository.Repository
import com.example.digikala.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _customer = MutableLiveData<Resources<CustomerDto>>()
    val customer: LiveData<Resources<CustomerDto>> = _customer

    private val _getEmailCustomer = MutableLiveData<Resources<List<CustomerDto>>>()
    val getEmailCustomer: LiveData<Resources<List<CustomerDto>>> = _getEmailCustomer

    private val _searchByEmail = MutableLiveData<Resources<List<OrderDto>>>()
    val searchByEmail: LiveData<Resources<List<OrderDto>>> = _searchByEmail

    private val _order = MutableLiveData<Resources<OrderDto>>()
    val order: LiveData<Resources<OrderDto>> = _order

    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val email = MutableLiveData("")


    fun createCustomer() {
        createCustomerByEmail(CustomerDto(firstName.value!!, lastName.value!!, email.value!!))
    }

    fun getCustomersByEmail() {
        viewModelScope.launch {
            _getEmailCustomer.postValue(Resources.Loading())
            val response = repository.getCustomersByEmail(email.value!!)
            _getEmailCustomer.postValue(handleCustomerResponseList(response))
        }
    }

    private fun createCustomerByEmail(dto: CustomerDto) {
        viewModelScope.launch {
            _customer.postValue(Resources.Loading())
            val response = repository.createCustomer(dto)
            _customer.postValue(handleCustomerResponse(response))
        }
    }

    fun getOrdersByEmail() {
        viewModelScope.launch {
            _searchByEmail.postValue(Resources.Loading())
            val response = repository.getOrdersByEmail(email.value!!)
            _searchByEmail.postValue(handleOrderResponse(response))
        }
    }

    fun createOrders() {
        val billing = Billing(email = email.value!!)
        val order = OrderDto(billing)
        viewModelScope.launch {
            _order.postValue(Resources.Loading())
            val response = repository.createOrders(order)
            _order.postValue(handleOrderResponseList(response))
        }
    }

    fun checkCustomerEntries() =
        run { !(firstName.value!!.isEmpty() || lastName.value!!.isEmpty() || email.value!!.isEmpty()) }

    private fun handleCustomerResponse(response: Response<CustomerDto>): Resources<CustomerDto> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    private fun handleCustomerResponseList(response: Response<List<CustomerDto>>): Resources<List<CustomerDto>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    private fun handleOrderResponse(response: Response<List<OrderDto>>): Resources<List<OrderDto>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    private fun handleOrderResponseList(response: Response<OrderDto>): Resources<OrderDto> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }
}