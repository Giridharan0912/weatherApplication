package com.example.retrofitdummy.repository

import android.util.Log
import com.example.retrofitdummy.common.Constants.ONE_HOUR_IN_MILLIS
import com.example.retrofitdummy.common.ResponseState
import com.example.retrofitdummy.model.dbmodel.*
import com.example.retrofitdummy.model.domainmodel.DomainBaseModel
import com.example.retrofitdummy.model.networkmodel.*
import com.example.retrofitdummy.retrofit.RetrofitInstance
import com.example.retrofitdummy.room.RoomDb
import retrofit2.Response
import java.util.*

class WeatherRepository(
    private val db: RoomDb,
    private val apiKey: String
) {
    private val TAG = WeatherRepository::class.java.simpleName


    suspend fun getWeatherData(
        lat: String,
        lon: String,
        apiKey: String
    ): ResponseState<DomainBaseModel> =
        getDataOnAvailability(lat, lon, apiKey)


    private suspend fun callNetworkWeatherData(
        lat: String,
        lon: String,
        apiKey: String
    ): ResponseState<DomainBaseModel> =
        fetchData(lat, lon, apiKey)

    private suspend fun handleWeatherData(response: Response<NetworkWeatherResponse>): ResponseState<DomainBaseModel> {
        if (response.isSuccessful) {
            response.body()?.let { networkWeatherResponse ->
                val id = "${networkWeatherResponse.lat}_${networkWeatherResponse.lon}".hashCode()
                insertWeatherModel(networkWeatherResponse.asDatabaseWeatherModel(id))
                insertCurrentWeather(networkWeatherResponse.current!!.asDatabaseCurrentWeather(id))
                insertHourlyWeather(networkWeatherResponse.hourly!!.asDatabaseHourlyWeather(id))
                insertDailyWeather(networkWeatherResponse.daily!!.asDatabaseDailyWeather(id))
                ResponseState.Success(getDataFromDb(id))
            }
        }
        return ResponseState.Failure(message = "Error occurred")
    }

    private suspend fun fetchData(
        lat: String,
        lon: String,
        apiKey: String
    ): ResponseState<DomainBaseModel> {
        try {
            val response = RetrofitInstance.retroApi.getWeather(lat, lon, apiKey)
            return handleWeatherData(response)
        } catch (e: Exception) {
            Log.d(TAG, "fetchData: Error ${e.localizedMessage}")
            return ResponseState.Failure(message = "${e.localizedMessage}")
        }
    }


//INSERT

    private suspend fun insertWeatherModel(databaseWeatherModel: DatabaseWeatherModel) {
        db.getRoomDao().insertWeatherModel(databaseWeatherModel)
    }

    private suspend fun insertCurrentWeather(databaseCurrentWeather: DatabaseCurrentWeather) {
        db.getRoomDao().insertCurrentWeather(databaseCurrentWeather)
    }

    private suspend fun insertHourlyWeather(databaseHourlyWeather: List<DatabaseHourlyWeather>) {
        db.getRoomDao().insertHourlyWeather(databaseHourlyWeather)
    }

    private suspend fun insertDailyWeather(databaseDailyWeather: List<DatabaseDailyWeather>) {
        db.getRoomDao().insertDailyWeather(databaseDailyWeather)
    }


//QUERY

    private suspend fun getWeatherModel(primaryId: Int): DatabaseWeatherModel? =
        db.getRoomDao().getWeatherModel(primaryId)

    private suspend fun getHourlyWeather(primaryId: Int): WeatherModelWithHourly? =
        db.getRoomDao().getHourlyWeather(primaryId)

    private suspend fun getDailyWeather(primaryId: Int): WeatherModelWithDaily? =
        db.getRoomDao().getDailyWeather(primaryId)

    private suspend fun getCurrentWeather(primaryId: Int): DatabaseCurrentWeather? =
        db.getRoomDao().getCurrentWeather(primaryId)

//  Logic to fetch data from db or api

    private suspend fun isAvailableInDb(primaryId: Int): Boolean =
        getWeatherModel(primaryId) != null


    private suspend fun checkForUpdate(primaryId: Int): ResponseState<DomainBaseModel> {
        val hourlyWeather = getHourlyWeather(primaryId)
        return if (hourlyWeather != null) {
            val hourlyModelLastUpdated = hourlyWeather.hourlyWeather[0].dt * 1000L
            if (Date().time > (hourlyModelLastUpdated + ONE_HOUR_IN_MILLIS)) {
                callNetworkWeatherData(
                    hourlyWeather.weatherModel.lat.toString(),
                    hourlyWeather.weatherModel.lon.toString(),
                    apiKey
                )
            } else {
                getDataFromDb(primaryId)
            }

        } else {
            ResponseState.Failure(message = "HourlyWeatherDb is null")
        }

    }


    private suspend fun getDataOnAvailability(
        lat: String,
        lon: String,
        apiKey: String
    ): ResponseState<DomainBaseModel> {
        val id = "${lat}_${lon}".hashCode()
        return if (isAvailableInDb(id)) {
            checkForUpdate(id)
        } else {
            callNetworkWeatherData(lat, lon, apiKey)
        }
    }


    private suspend fun getDataFromDb(primaryId: Int): ResponseState<DomainBaseModel> {
        val weatherModelWithHourly = getHourlyWeather(primaryId)
        val weatherModelWithDaily = getDailyWeather(primaryId)
        val currentWeather = getCurrentWeather(primaryId)
        val weatherModel = weatherModelWithHourly?.weatherModel
        val domainModelObj = DomainBaseModel(
            weatherModel?.lat,
            weatherModel?.lon,
            weatherModel?.timezone,
            weatherModel?.timezone_offset,
            currentWeather?.asDomainCurrentWeather(),
            weatherModelWithHourly?.hourlyWeather?.asDomainHourlyWeather(),
            weatherModelWithDaily?.dailyWeather?.asDomainDailyWeather()
        )
        return ResponseState.Success(domainModelObj)
    }
}