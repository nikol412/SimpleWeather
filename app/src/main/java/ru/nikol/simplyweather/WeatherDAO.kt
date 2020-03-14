package ru.nikol.simplyweather

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDAO {
    @Query("SELECT * FROM weather_items")
    fun getAll(): List<WeatherEntity>

    @Query("SELECT * FROM weather_items WHERE city LIKE :sCity")
    fun findByCity(sCity: String): List<WeatherEntity>

    @Insert
    fun insertAll(vararg todo: WeatherEntity)

    @Delete
    fun delete(weather: WeatherEntity)

    @Query("DELETE FROM weather_items")
    fun deleteAll()

    @Query("SELECT * FROM weather_items order by id desc limit 1")
    fun getLast():WeatherEntity
}