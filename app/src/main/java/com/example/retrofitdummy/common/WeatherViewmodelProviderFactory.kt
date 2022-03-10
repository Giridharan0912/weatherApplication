package com.example.retrofitdummy.common

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdummy.repository.WeatherRepository
import com.example.retrofitdummy.viewmodel.WeatherViewmodel


class WeatherViewmodelProviderFactory(val application: Application, val repo: WeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewmodel(application, repo) as T
    }
}