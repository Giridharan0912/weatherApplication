package com.example.retrofitdummy

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdummy.common.Constants.API_KEY
import com.example.retrofitdummy.common.WeatherViewmodelProviderFactory
import com.example.retrofitdummy.repository.WeatherRepository
import com.example.retrofitdummy.room.RoomDb
import com.example.retrofitdummy.viewmodel.WeatherViewmodel

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    lateinit var viewmodel: WeatherViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        API_KEY = (ai.metaData["weatherAPI"]).toString()
        Log.d(TAG, "onCreate: $API_KEY")


        val weatherRepository = WeatherRepository(RoomDb(this), API_KEY)
        val viewModelFactory =
            WeatherViewmodelProviderFactory(application, weatherRepository)
        val weatherViewmodel =
            ViewModelProvider(this, viewModelFactory)[WeatherViewmodel::class.java]

        weatherViewmodel.getWeatherData("12.961710401803964", "80.18675020081213", API_KEY)


    }
}