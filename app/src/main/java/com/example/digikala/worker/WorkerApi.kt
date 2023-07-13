package com.example.digikala.worker

import com.example.digikala.data.models.products.ProductsResponseItem
import com.example.digikala.util.Const.API_KEY
import com.example.digikala.util.Const.API_SECRET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkerApi {
    @GET("products")
    suspend fun getNewProductList(
        @Query("after") after: String,
        @Query("consumer_key") consumer_key: String = API_KEY,
        @Query("consumer_secret") consumer_secret: String = API_SECRET,
    ): Response<List<ProductsResponseItem>>
}