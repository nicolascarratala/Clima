package com.example.clima.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clima.api.ClimaService
import com.example.clima.data.Clima
import com.example.clima.utils.Constants
import com.example.mispeliculas.api.RetrofitServiceBuilder
import retrofit2.Call
import retrofit2.Response

object ClimaRepository {

    private val climaService: ClimaService =
        RetrofitServiceBuilder(Constants.BASE_URL)
            .buildService(ClimaService::class.java)

    val climaCompleto = MutableLiveData<Clima?>()

    fun obtenerClimaActual(long: Double, lat: Double ): LiveData<Clima?> {
        try {

            val call = climaService.obtenerClima(lat.toString(),long.toString(),Constants.API_KEY_WEATHER)
            call.enqueue(object: retrofit2.Callback<Clima> {
                override fun onResponse(call: Call<Clima>, response: Response<Clima>) {
                    if(response.isSuccessful){
                        val clima = response.body()
                        Log.d("clima", clima.toString())
                        climaCompleto.value = clima
                    }
                }

                override fun onFailure(call: Call<Clima>, t: Throwable) {
                    Log.e("ERROR API", "Error clima actual")
                }

            })

        }catch(error: Error){
            Log.e("error", error.toString())
        }
        return climaCompleto
    }
}