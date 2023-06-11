package com.example.digikala.data.repository

import com.example.digikala.data.models.category.CategoryResponse
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
}