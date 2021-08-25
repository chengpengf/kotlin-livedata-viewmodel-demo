package com.example.myapplication.retrofit

import com.example.myapplication.`interface`.ApiService
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    const val BASE_URL = "https://www.wanandroid.com/" // http://localhost:8080/
    val okHttpClient: OkHttpClient by lazy {
        RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    private val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        return api
    }
}