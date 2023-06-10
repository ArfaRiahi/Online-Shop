package com.example.digikala.data.remote

import com.example.digikala.data.models.category.CategoryResponse
import com.example.digikala.data.models.products.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApiService {

    //getting Products Items Id
    @GET("products")
    suspend fun getProductsItemsId(@Query("include") id: Int): Response<ProductsResponse>

    //getting Newest Products
    @GET("products/")
    suspend fun getNewestProducts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("orderby") orderBy: String = "date"
    ): Response<ProductsResponse>

    //getting Most Visited Products
    @GET("products/")
    suspend fun getMostVisitedProducts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("orderby") orderBy: String = "popularity"
    ): Response<ProductsResponse>

    //getting Top Rated Products
    @GET("products/")
    suspend fun getTopRatedProducts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("orderby") orderBy: String = "rating"
    ): Response<ProductsResponse>

    //getting ProductsCategories
    @GET("products/categories")
    suspend fun getProductsCategories(): Response<CategoryResponse>

    //getting products from special category
    @GET("products/")
    suspend fun getProductsLists(
        @Query("category") category: Int
    ): Response<ProductsResponse>

    //getting slider info
    @GET("products/")
    suspend fun getSliderProducts(
        @Query("category") category: Int = 119
    ): Response<ProductsResponse>
}