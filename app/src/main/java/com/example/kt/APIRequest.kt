package com.example.kt

import com.example.kt.Api.Restaurant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIRequest {

    @GET("v3/businesses/search?location=izmir&&limit=7")
    suspend fun getRestaurant(): Restaurant

    @GET
    suspend fun searchRestaurants(@Url Url: String): Restaurant
}