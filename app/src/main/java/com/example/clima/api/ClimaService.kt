package com.example.clima.api

import com.example.clima.data.Clima
import com.example.clima.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaService {

    @GET("weather?")
    fun obtenerClima(
        @Query("lat")
        lat: String,
        @Query("lon")
        lon: String,
        @Query("appid")
        appid: String

    ): Call<Clima>


}