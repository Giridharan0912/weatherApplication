package com.example.retrofitdummy.common


import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object Constants {
    lateinit var API_KEY: String
    const val BASE_URL = "https://api.openweathermap.org"

    const val ONE_HOUR_IN_MILLIS=3600000
}

//
//internal fun toLong(date: Date): Long = date.time
//
//
//private fun toMilliseconds(date: String?): Long {
//    val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy ")
//    try {
//        val mDate: Date = sdf.parse(date)
//        val timeInMilliseconds = mDate.time
//        println("Date in milli :: $timeInMilliseconds")
//        return timeInMilliseconds
//    } catch (e: ParseException) {
//        e.printStackTrace()
//    }
//    return 0
//}
//
//
//internal fun toTimeInMilliseconds(date: Date): Long {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy")
//        val localDate: LocalDateTime = LocalDateTime.parse(date.toString(), formatter)
//        return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
//    } else {
//        return toMilliseconds(date.toString())
//    }
//}