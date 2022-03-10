package com.example.retrofitdummy.model.networkmodel

import com.example.retrofitdummy.model.dbmodel.*
import java.util.*

data class NetworkWeatherResponse(
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
    val current: NetworkCurrentWeather? = null,
    val hourly: MutableList<NetworkHourlyWeather>? = null,
    val daily: MutableList<NetworkDailyWeather>? = null
)

data class NetworkCurrentWeather(
    val dt: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val temp: Double = 0.0,
    val feels_like: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val dew_point: Double = 0.0,
    val uvi: Double = 0.0,
    val clouds: Int = 0,
    val visibility: Int = 0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val weather: ArrayList<NetworkWeather>? = null
)

data class NetworkDailyWeather(

    val dt: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val moonrise: Int = 0,
    val moonset: Int = 0,
    val moon_phase: Double = 0.0,
    val temp: NetworkTemp? = null,
    val feels_like: NetworkFeelsLike? = null,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val dew_point: Double = 0.0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val weather: ArrayList<NetworkWeather>? = null,
    val clouds: Int = 0,
    val pop: Double = 0.0,
    val rain: Double = 0.0,
    val uvi: Double = 0.0
)

data class NetworkHourlyWeather(

    val dt: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val temp: Double = 0.0,
    val feels_like: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val dew_point: Double = 0.0,
    val uvi: Double = 0.0,
    val clouds: Int = 0,
    val visibility: Int = 0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val weather: ArrayList<NetworkWeather>? = null
)


data class NetworkFeelsLike(
    val day: Double = 0.0,
    val night: Double = 0.0,
    val eve: Double = 0.0,
    val morn: Double = 0.0
)


data class NetworkTemp(
    val day: Double = 0.0,
    val min: Double = 0.0,
    val max: Double = 0.0,
    val night: Double = 0.0,
    val eve: Double = 0.0,
    val morn: Double = 0.0
)

data class NetworkWeather(
    val id: Int = 0,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

fun NetworkCurrentWeather.asDatabaseCurrentWeather(primaryId: Int): DatabaseCurrentWeather =
    DatabaseCurrentWeather(
        primaryId = primaryId,
        dt = dt,
        sunrise = sunrise,
        sunset = sunset,
        temp = temp,
        feels_like = feels_like,
        pressure = pressure,
        humidity = humidity,
        dew_point = dew_point,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        wind_speed = wind_speed,
        wind_deg = wind_deg,
        wind_gust = wind_gust,
        weather = weather?.asDatabaseWeather()
    )

fun List<NetworkHourlyWeather>.asDatabaseHourlyWeather(primaryId: Int): List<DatabaseHourlyWeather> =
    this.map {
        DatabaseHourlyWeather(
            primaryId = primaryId,
            dt = it.dt,
            sunrise = it.sunrise,
            sunset = it.sunset,
            temp = it.temp,
            feels_like = it.feels_like,
            pressure = it.pressure,
            humidity = it.humidity,
            dew_point = it.dew_point,
            uvi = it.uvi,
            clouds = it.clouds,
            visibility = it.visibility,
            wind_speed = it.wind_speed,
            wind_deg = it.wind_deg,
            wind_gust = it.wind_gust,
            weather = it.weather?.asDatabaseWeather()
        )
    }

fun ArrayList<NetworkWeather>.asDatabaseWeather(
): List<DatabaseWeather> =
    this.map {
        DatabaseWeather(
            it.main,
            it.description, it.icon
        )
    }


fun List<NetworkDailyWeather>.asDatabaseDailyWeather(primaryId: Int): List<DatabaseDailyWeather> =
    this.map {
        DatabaseDailyWeather(
            primaryId = primaryId,
            dt = it.dt,
            sunrise = it.sunrise,
            sunset = it.sunset,
            moonrise = it.moonrise,
            moonset = it.moonset,
            moon_phase = it.moon_phase,
            pressure = it.pressure,
            humidity = it.humidity,
            dew_point = it.dew_point,
            wind_speed = it.wind_speed,
            wind_deg = it.wind_deg,
            wind_gust = it.wind_gust,
            clouds = it.clouds,
            pop = it.pop,
            rain = it.rain,
            weather = it.weather?.asDatabaseWeather(),
            temp = it.temp?.asDatabaseTemp(),
            feels_like = it.feels_like?.asDatabaseFeelsLike()
        )
    }


fun NetworkTemp.asDatabaseTemp(): DatabaseTemp =
    DatabaseTemp(
        day, min, max, night, eve, morn
    )

fun NetworkFeelsLike.asDatabaseFeelsLike(): DatabaseFeelsLike =
    DatabaseFeelsLike(
        day, night, eve, morn
    )


fun NetworkWeatherResponse.asDatabaseWeatherModel(primaryId: Int): DatabaseWeatherModel =
    DatabaseWeatherModel(
        primaryId,
        lat,
        lon,
        timezone,
        timezone_offset,
        Date().time
    )



