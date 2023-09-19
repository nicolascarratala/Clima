package com.example.clima.di

import com.example.clima.ui.mapa.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ApplicationGraph {

    fun inject(target: MainActivity) // for field inject property inside the MainActivity

    /** Dagger will check the MainActivity for the fields inside to provide values. Inorder to do that we need this inject method.
     * */



}