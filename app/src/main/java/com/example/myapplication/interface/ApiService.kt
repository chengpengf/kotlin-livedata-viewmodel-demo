package com.example.myapplication.`interface`

import com.example.myapplication.bean.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("wxarticle/chapters/json")
    suspend fun getFictions(): BaseResp<List<Fiction>>

    @GET("banner/json")
    suspend fun getWanAndroidBanner(): BaseResp<List<WanAndroidBannerBean>>

    @Headers("Domain-Name:cpf")
    @GET("/news/banner.json")
    suspend fun getBanner(): BaseNewsResp<List<BannerBean>>

    @Headers("Domain-Name:cpf")
    @GET("news/everyday_format.json")
    suspend fun getNews(): BaseNewsResp<List<NewsBean>>

    @Headers("Domain-Name:cpf")
    @GET("/news/topic_list.json")
    suspend fun getTabs(): BaseTabResp<List<EduTabBean>>

    @Headers("Domain-Name:cpf")
    @GET("/news/{userId}")
    suspend fun getTopicData(@Path("userId") userId: String?): BaseNewsResp<List<EduItemBean>>

    @Headers("Domain-Name:cpf")
    @GET("mdapi/rest/market/3.0/optionalMarketData")
    suspend fun getOptionalMarketData(@Query("instrumentList") instrumentList: String?): BaseMarket<List<String>>
}