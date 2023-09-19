package com.example.clima.ui.clima

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.databinding.DataBindingUtil

import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.example.clima.R

import com.example.clima.databinding.ClimaBinding

import com.example.clima.utils.LocationHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


lateinit var viM: ClimaViewModel

private lateinit var binding: ClimaBinding

private lateinit var locationHelper: LocationHelper

private var lon = ""
private var lat = ""

class ClimaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clima)

        locationHelper = LocationHelper(this)

        binding = DataBindingUtil.setContentView(this, R.layout.clima)
        viM = ViewModelProvider(this).get(ClimaViewModel::class.java)

        binding.vm = viM

        viM.clima.observe(this, Observer {

            val temperatureFahrenheit = it!!.main?.temp
            val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
            val temperatureFormatted = String.format("%.2f", temperatureCelsius)

            for (i in it.weather) {

                binding.descMain.text = i.description

            }
            binding.cityName.text = it.name.toString()
            binding.tempMain.text = "$temperatureFormatted°"
            binding.humidityMain.text = it.main!!.humidity.toString()
            binding.windSpeed.text = it.wind?.speed.toString()


            val timestamp = (it.dt?.toLong() ?: 0) * 1000 // Multiplicar por 1000 para convertir segundos a milisegundos
            val date = Date(timestamp)
            val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale.getDefault())
            val dateanddayname = outputFormat.format(date)

            binding.dateDayMain.text = dateanddayname


            // setting the icon
            for (i in it.weather) {

                if (i.icon == "01d") {
                    binding.imageMain.setImageResource(R.drawable.oned)
                }
                if (i.icon == "01n") {
                    binding.imageMain.setImageResource(R.drawable.onen)
                }
                if (i.icon == "02d") {
                    binding.imageMain.setImageResource(R.drawable.twod)
                }
                if (i.icon == "02n") {
                    binding.imageMain.setImageResource(R.drawable.twon)
                }
                if (i.icon == "03d" || i.icon == "03n") {
                    binding.imageMain.setImageResource(R.drawable.threedn)
                }
                if (i.icon == "10d") {
                    binding.imageMain.setImageResource(R.drawable.tend)
                }
                if (i.icon == "10n") {
                    binding.imageMain.setImageResource(R.drawable.tenn)
                }
                if (i.icon == "04d" || i.icon == "04n") {
                    binding.imageMain.setImageResource(R.drawable.fourdn)
                }
                if (i.icon == "09d" || i.icon == "09n") {
                    binding.imageMain.setImageResource(R.drawable.ninedn)
                }
                if (i.icon == "11d" || i.icon == "11n") {
                    binding.imageMain.setImageResource(R.drawable.elevend)
                }
                if (i.icon == "13d" || i.icon == "13n") {
                    binding.imageMain.setImageResource(R.drawable.thirteend)
                }
                if (i.icon == "50d" || i.icon == "50n") {
                    binding.imageMain.setImageResource(R.drawable.fiftydn)
                }

            }
        })
    }
}