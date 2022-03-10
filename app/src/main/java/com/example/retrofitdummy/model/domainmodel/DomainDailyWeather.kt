package com.example.retrofitdummy.model.domainmodel

data class DomainDailyWeather(
    val dt: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val moonrise: Int = 0,
    val moonset: Int = 0,
    val moon_phase: Double = 0.0,
    val temp: DomainTemp? = null,
    val feels_like: DomainFeelsLike? = null,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val dew_point: Double = 0.0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val weather: List<DomainWeather>? = null,
    val clouds: Int = 0,
    val pop: Double = 0.0,
    val rain: Double = 0.0,

    val uvi: Double = 0.0
)
