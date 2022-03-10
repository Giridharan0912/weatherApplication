package com.example.retrofitdummy.model.dbmodel

import androidx.annotation.NonNull
import androidx.room.*
import com.example.retrofitdummy.model.domainmodel.*
import com.example.retrofitdummy.model.networkmodel.*
import java.util.*


@Entity
data class DatabaseWeatherModel(
    @NonNull
    @PrimaryKey
    var primaryId: Int,
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
    val lastUpdated: Long? = null
)


@Entity(
    foreignKeys = [ForeignKey(
        entity = DatabaseWeatherModel::class,
        parentColumns = arrayOf("primaryId"),
        childColumns = arrayOf("primaryId")
    )]
)

data class DatabaseCurrentWeather(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var currentWeatherId: Int? = null,
    val primaryId: Int,
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
    val weather: List<DatabaseWeather>? = null
)


@Entity(
    foreignKeys = [ForeignKey(
        entity = DatabaseWeatherModel::class, parentColumns =
        arrayOf("primaryId"),
        childColumns = arrayOf("primaryId")
    )]
)
data class DatabaseDailyWeather(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var dailyId: Int? = null,
    val primaryId: Int,
    val dt: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val moonrise: Int = 0,
    val moonset: Int = 0,
    val moon_phase: Double = 0.0,
    val temp: DatabaseTemp? = null,
    val feels_like: DatabaseFeelsLike? = null,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val dew_point: Double = 0.0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val weather: List<DatabaseWeather>? = null,
    val clouds: Int = 0,
    val pop: Double = 0.0,
    val rain: Double = 0.0,
    val uvi: Double = 0.0,

    )


@Entity(
    foreignKeys = [ForeignKey(
        entity = DatabaseWeatherModel::class, parentColumns =
        arrayOf("primaryId"),
        childColumns = arrayOf("primaryId")
    )]
)
data class DatabaseHourlyWeather(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var hourlyId: Int? = null,
    val primaryId: Int,
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
    val weather: List<DatabaseWeather>? = null
)

data class DatabaseFeelsLike(
    val day: Double = 0.0,
    val night: Double = 0.0,
    val eve: Double = 0.0,
    val morn: Double = 0.0
)


data class DatabaseTemp(
    val day: Double = 0.0,
    val min: Double = 0.0,
    val max: Double = 0.0,
    val night: Double = 0.0,
    val eve: Double = 0.0,
    val morn: Double = 0.0
)


data class DatabaseWeather(
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)


//one to many relation

data class WeatherModelWithHourly(
    @Embedded val weatherModel: DatabaseWeatherModel,
    @Relation(
        entity = DatabaseHourlyWeather::class,
        parentColumn = "primaryId",
        entityColumn = "primaryId"
    )
    val hourlyWeather: List<DatabaseHourlyWeather>
)


data class WeatherModelWithDaily(
    @Embedded val weatherModel: DatabaseWeatherModel,
    @Relation(
        entity = DatabaseDailyWeather::class,
        parentColumn = "primaryId",
        entityColumn = "primaryId"
    )
    val dailyWeather: List<DatabaseDailyWeather>
)


/*
//One-one Relation

data class CurrentWithWeather(
    @Embedded val current: DatabaseCurrentWeather,
    @Relation(
        parentColumn = "currentWeatherId",
        entityColumn = "currentWeatherId"
    )
    val weather: DatabaseWeather
)

data class HourlyWithWeather(
    @Embedded val current: DatabaseHourlyWeather,
    @Relation(
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val weather: DatabaseWeather
)

data class DailyWithWeather(
    @Embedded val current: DatabaseDailyWeather,
    @Relation(
        parentColumn = "dailyId",
        entityColumn = "dailyId"
    )
    val weather: DatabaseWeather
)
 */

fun DatabaseCurrentWeather.asDomainCurrentWeather(): DomainCurrentWeather =
    DomainCurrentWeather(
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
        weather = weather?.asDomainWeather()
    )

fun List<DatabaseHourlyWeather>.asDomainHourlyWeather(): List<DomainHourlyWeather> =
    this.map {
        DomainHourlyWeather(
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
            weather = it.weather?.asDomainWeather()
        )
    }

fun List<DatabaseWeather>.asDomainWeather(
): List<DomainWeather> =
    this.map {
        DomainWeather(
            it.main,
            it.description, it.icon
        )
    }


fun List<DatabaseDailyWeather>.asDomainDailyWeather(): List<DomainDailyWeather> =
    this.map {
        DomainDailyWeather(
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
            weather = it.weather?.asDomainWeather(),
            temp = it.temp?.asDomainTemp(),
            feels_like = it.feels_like?.asDomainFeelsLike()
        )
    }


fun DatabaseTemp.asDomainTemp(): DomainTemp =
    DomainTemp(
        day, min, max, night, eve, morn
    )

fun DatabaseFeelsLike.asDomainFeelsLike(): DomainFeelsLike =
    DomainFeelsLike(
        day, night, eve, morn
    )


