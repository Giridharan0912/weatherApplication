package com.example.retrofitdummy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.retrofitdummy.common.ResponseState
import com.example.retrofitdummy.model.domainmodel.DomainBaseModel
import com.example.retrofitdummy.model.domainmodel.DomainCurrentWeather
import com.example.retrofitdummy.model.domainmodel.DomainDailyWeather
import com.example.retrofitdummy.model.domainmodel.DomainHourlyWeather
import com.example.retrofitdummy.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewmodel(application: Application, val repo: WeatherRepository) :
    AndroidViewModel(application) {
    private val TAG = WeatherViewmodel::class.java.simpleName
    val currentWeatherLV: MutableLiveData<ResponseState<DomainCurrentWeather>> = MutableLiveData()
    val hourlyWeatherLV: MutableLiveData<ResponseState<List<DomainHourlyWeather>>> =
        MutableLiveData()
    val dailyWeatherLV: MutableLiveData<ResponseState<List<DomainDailyWeather>>> = MutableLiveData()
    val weatherResponse: DomainBaseModel? = null


    fun getWeatherData(lat: String, lon: String, apiKey: String) = viewModelScope.launch {
        fetchData(lat, lon, apiKey)

    }

    private suspend fun handleWeatherData(response: ResponseState<DomainBaseModel>) {
//       TODO
    }

    private suspend fun fetchData(lat: String, lon: String, apiKey: String) {
        try {
            val response = repo.getWeatherData(lat, lon, apiKey)
            handleWeatherData(response = response)
        } catch (e: Exception) {
//TODO
        }
    }

}

