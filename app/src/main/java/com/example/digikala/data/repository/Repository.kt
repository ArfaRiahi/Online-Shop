package com.example.digikala.data.repository

import com.example.digikala.data.models.category.CategoryResponse
import com.example.digikala.data.models.products.ProductsResponse
import com.example.digikala.data.remote.ProductsApiService
import javax.inject.Inject

class Repository @Inject constructor(private val appService: ProductsApiService) {

    suspend fun getIdItemsProducts(id: Int): ProductsResponse {
        return appService.getProductsItemsId(id)
    }

    suspend fun getNewestProducts(page: Int, perPage: Int): ProductsResponse {
        return appService.getNewestProducts(page, perPage)
    }

    suspend fun getMostVisitedProducts(page: Int, perPage: Int): ProductsResponse {
        return appService.getMostVisitedProducts(page, perPage)
    }

    suspend fun getTopRatedProducts(page: Int, perPage: Int): ProductsResponse {
        return appService.getTopRatedProducts(page, perPage)
    }

    suspend fun getCategoryLists(): CategoryResponse {
        return appService.getProductsCategories()
    }
}