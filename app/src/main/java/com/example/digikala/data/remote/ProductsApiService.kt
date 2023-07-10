package com.example.digikala.data.remote

import com.example.digikala.data.models.category.CategoryResponse
import com.example.digikala.data.models.customer.CustomerDto
import com.example.digikala.data.models.order.OrderDto
import com.example.digikala.data.models.products.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15,
        @Query("category") category: String = "119"
    ): Response<ProductsResponse>

    //search product general
    @GET("products")
    suspend fun getAllSearchProduct(
        @Query("search") search: String,
        @Query("orderby") orderBy: String
    ): Response<ProductsResponse>

    @GET("products")
    suspend fun getAllSearchProductPrice(
        @Query("search") search: String,
    ): Response<ProductsResponse>

    @POST("customers")
    @Headers("Content-Type: application/json")
    suspend fun createCustomer(@Body customerDto: CustomerDto): Response<CustomerDto>

    @GET("customers")
    suspend fun getCustomersByEmail(@Query("email") email: String): Response<List<CustomerDto>>

    @POST("orders")
    suspend fun createOrders(@Body orderDto: OrderDto): Response<OrderDto>

    @GET("orders")
    suspend fun getOrdersByEmail(
        @Query("search") searchEmail: String,
        @Query("status") status: String = "processing",
        @Query("orderby") orderby: String = "date",
    ): Response<List<OrderDto>>

    @PUT("orders/{id}")
    suspend fun putUpdateOrder(
        @Path("id") id: Int,
        @Body customerOrder: OrderDto
    ): Response<OrderDto>

    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: Int): Response<OrderDto>
}