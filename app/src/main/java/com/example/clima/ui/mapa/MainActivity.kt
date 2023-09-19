package com.example.clima.ui.mapa

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.clima.R
import com.example.clima.ui.clima.ClimaActivity
import com.example.clima.utils.Constants
import com.example.clima.utils.LocationHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.Manifest
import com.example.clima.repository.ClimaRepository
import com.example.clima.ui.clima.viM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() , OnMapReadyCallback{


    private lateinit var locationHelper: LocationHelper


    private var map : GoogleMap? = null

    private var lon : Double = 0.0
    private var lat : Double = 0.0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        locationHelper = LocationHelper(this)

        if (locationHelper.isLocationPermissionGranted()) {
            // Permission is granted, request location updates
            requestLocationUpdates()
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }

         val mapFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment?
            val btClima = findViewById<Button>(R.id.btClima)

        btClima.setOnClickListener {
            val intent = Intent(this, ClimaActivity::class.java).apply {
                putExtra("EXTRA_MESSAGE", "message")

            }
            startActivity(intent)
        }

        mapFragment?.getMapAsync(this)


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestLocationUpdates()  {
        locationHelper.requestLocationUpdates { location ->
            // Log latitude and longitude here
            val latitude = location.latitude
            val longitude = location.longitude

            lat = latitude
            lon = longitude

            logLocation(latitude, longitude)

            this.map?.apply {
                val location = LatLng(lat,lon)
                addMarker(
                    MarkerOptions()
                        .position(location)
                        .title("")
                )

                moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0F))
            }

            ClimaRepository.obtenerClimaActual(lon, lat)
        }


    }

    private fun logLocation(latitude: Double, longitude: Double) {
        // Log the latitude and longitude
        val message = "Latitude: $latitude, Longitude: $longitude"

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, request location updates
                requestLocationUpdates()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(
                    this,
                    "Location permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onMapReady(p0: GoogleMap) {
        this.map = p0

    }
}