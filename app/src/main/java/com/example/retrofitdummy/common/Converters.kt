package com.example.retrofitdummy.common

import androidx.room.TypeConverter
import com.example.retrofitdummy.model.dbmodel.DatabaseFeelsLike
import com.example.retrofitdummy.model.dbmodel.DatabaseTemp
import com.example.retrofitdummy.model.dbmodel.DatabaseWeather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverter
fun fromWeather(weather: ArrayList<DatabaseWeather>?): String? {
    val type = object : TypeToken<ArrayList<DatabaseWeather>>() {}.type
    return Gson().toJson(weather, type)
}

@TypeConverter
fun toWeather(weatherStr: String?): ArrayList<DatabaseWeather>? {
    val type = object : TypeToken<String>() {}.type
    return Gson().fromJson<ArrayList<DatabaseWeather>>(weatherStr, type)
}

@TypeConverter
fun fromFeelsLike(feelsLike: DatabaseFeelsLike?): String? {
    val type = object : TypeToken<DatabaseFeelsLike>() {}.type
    return Gson().toJson(feelsLike, type)
}

@TypeConverter
fun toFeelsLike(feelsLikeStr: String?): DatabaseFeelsLike? {
    val type = object : TypeToken<String>() {}.type
    return Gson().fromJson<DatabaseFeelsLike>(feelsLikeStr, type)
}

@TypeConverter
fun fromTemp(temp: DatabaseTemp?): String? {
    val type = object : TypeToken<DatabaseTemp>() {}.type
    return Gson().toJson(temp, type)
}

@TypeConverter
fun toTemp(tempStr: String?): DatabaseFeelsLike? {
    val type = object : TypeToken<String>() {}.type
    return Gson().fromJson<DatabaseFeelsLike>(tempStr, type)
}