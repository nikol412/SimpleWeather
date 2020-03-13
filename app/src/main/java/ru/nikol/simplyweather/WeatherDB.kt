package ru.nikol.simplyweather

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WeatherEntity::class), version = 1)
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO

    companion object {
        @Volatile private var instance: WeatherDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            WeatherDB::class.java, "weather-list.db")
            .build()
    }
}
