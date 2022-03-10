package com.example.retrofitdummy.room

import androidx.room.*
import com.example.retrofitdummy.model.dbmodel.*


@Dao
interface RoomDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherModel(weatherModel: DatabaseWeatherModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: DatabaseCurrentWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(hourlyWeather: List<DatabaseHourlyWeather>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(dailyWeather: List<DatabaseDailyWeather>)


    @Query("SELECT* FROM DatabaseWeatherModel WHERE primaryId=:id")
    suspend fun getWeatherModel(id: Int): DatabaseWeatherModel?

    @Query("SELECT * FROM DatabaseCurrentWeather WHERE primaryId=:id")
    suspend fun getCurrentWeather(id: Int): DatabaseCurrentWeather?

    @Transaction
    @Query("SELECT * FROM DatabaseWeatherModel WHERE primaryId=:id")
    suspend fun getHourlyWeather(id: Int): WeatherModelWithHourly?

    @Transaction
    @Query("SELECT * FROM   DatabaseWeatherModel WHERE primaryId=:id")
    suspend fun getDailyWeather(id: Int): WeatherModelWithDaily?
}