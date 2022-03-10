package com.example.retrofitdummy.retrofit

import com.example.retrofitdummy.model.networkmodel.NetworkWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat")
        lat: String,
        @Query("lon")
        lon: String,
        @Query("appid")
        appid: String,
        @Query("exclude")
        exclude: String = "minutely",
        @Query("units")
        units: String = "metric"
    ): Response<NetworkWeatherResponse>

}