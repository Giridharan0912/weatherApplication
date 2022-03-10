package com.example.retrofitdummy.model.domainmodel

data class DomainWeather(
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)


data class DomainFeelsLike(
    val day: Double = 0.0,
    val night: Double = 0.0,
    val eve: Double = 0.0,
    val morn: Double = 0.0
)


data class DomainTemp(
    val day: Double = 0.0,
    val min: Double = 0.0,
    val max: Double = 0.0,
    val night: Double = 0.0,
    val eve: Double = 0.0,
    val morn: Double = 0.0
)