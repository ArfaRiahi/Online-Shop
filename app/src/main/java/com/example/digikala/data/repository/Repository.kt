package com.example.digikala.data.repository

import com.example.digikala.data.models.category.CategoryResponse
import com.example.digikala.data.models.customer.CustomerDto
import com.example.digikala.data.models.order.OrderDto
import com.example.digikala.data.models.products.ProductsResponse
import com.example.digikala.data.remote.ProductsApiService
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val appService: ProductsApiService) {

    suspend fun getIdItemsProducts(id: Int): Response<ProductsResponse> {
        return appService.getProductsItemsId(id)
    }

    suspend fun getNewestProducts(page: Int, perPage: Int): Response<ProductsResponse> {
        return appService.getNewestProducts(page, perPage)
    }

    suspend fun getMostVisitedProducts(page: Int, perPage: Int): Response<ProductsResponse> {
        return appService.getMostVisitedProducts(page, perPage)
    }

    suspend fun getTopRatedProducts(page: Int, perPage: Int): Response<ProductsResponse> {
        return appService.getTopRatedProducts(page, perPage)
    }

    suspend fun getCategoryLists(): Response<CategoryResponse> {
        return appService.getProductsCategories()
    }

    suspend fun getProductsList(productId: Int): Response<ProductsResponse> {
        return appService.getProductsLists(productId)
    }

    suspend fun getSliderProducts(): Response<ProductsResponse> {
        return appService.getSliderProducts()
    }

    suspend fun getSearchedProduct(search: String, orderBy: String): Response<ProductsResponse> {
        return appService.getAllSearchProduct(search, orderBy)
    }

    suspend fun getSearchedProductPrice(search: String): Response<ProductsResponse> {
        return appService.getAllSearchProductPrice(search)
    }


    suspend fun createCustomer(dto: CustomerDto): Response<CustomerDto> {
        return appService.createCustomer(dto)
    }

    suspend fun getCustomersByEmail(email: String): Response<List<CustomerDto>> {
        return appService.getCustomersByEmail(email)
    }

    suspend fun createOrders(order: OrderDto): Response<OrderDto> {
        return appService.createOrders(order)
    }

    suspend fun getOrdersByEmail(searchEmail: String): Response<List<OrderDto>> {
        return appService.getOrdersByEmail(searchEmail)
    }

    suspend fun putUpdateOrder(id: Int, customerOrder: OrderDto): Response<OrderDto> {
        return appService.putUpdateOrder(id, customerOrder)
    }

    suspend fun getOrderById(id: Int): Response<OrderDto> {
        return appService.getOrderById(id)
    }
}