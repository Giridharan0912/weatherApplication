package com.example.retrofitdummy.model.domainmodel

data class DomainBaseModel(
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
    val current: DomainCurrentWeather? = null,
    val hourly: List<DomainHourlyWeather>? = null,
    val daily: List<DomainDailyWeather>? = null
)
