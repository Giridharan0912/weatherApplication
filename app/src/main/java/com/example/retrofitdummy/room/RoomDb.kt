package com.example.retrofitdummy.room

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.retrofitdummy.model.dbmodel.DatabaseDailyWeather
import com.example.retrofitdummy.model.dbmodel.DatabaseHourlyWeather
import com.example.retrofitdummy.model.dbmodel.DatabaseWeatherModel


@Database(
    entities = [DatabaseWeatherModel::class, DatabaseHourlyWeather::class, DatabaseDailyWeather::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {

    abstract fun getRoomDao(): RoomDao

    companion object {
        @Volatile
        private var roomInstance: RoomDb? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = roomInstance ?: synchronized(LOCK) {
            roomInstance ?: createDatabase(context).also { it ->
                roomInstance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDb::class.java,
                "weather_db.db"
            ).build()

    }
}