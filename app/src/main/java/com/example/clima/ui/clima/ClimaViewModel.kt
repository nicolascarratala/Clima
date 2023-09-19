package com.example.clima.ui.clima

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clima.repository.ClimaRepository

class ClimaViewModel : ViewModel(){

    val clima = ClimaRepository.climaCompleto
}