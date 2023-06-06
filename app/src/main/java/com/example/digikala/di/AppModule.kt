package com.example.digikala.di

import com.example.digikala.data.remote.ProductsApiService
import com.example.digikala.util.Const.API_KEY
import com.example.digikala.util.Const.API_SECRET
import com.example.digikala.util.Const.BASE_URL
import com.example.digikala.util.Const.CONSUMER_KEY
import com.example.digikala.util.Const.CONSUMER_SECRET
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(CONSUMER_KEY, API_KEY)
                .addQueryParameter(CONSUMER_SECRET, API_SECRET)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideLoginInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ProductsApiService {
        return retrofit.create(ProductsApiService::class.java)
    }
}