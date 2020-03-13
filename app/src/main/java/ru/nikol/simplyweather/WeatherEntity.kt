package ru.nikol.simplyweather

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class WeatherEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "windSpeed") var windSpeed: Double,
    @ColumnInfo(name = "windDegrees") var windDegrees: Int,
    @ColumnInfo(name = "pressure") var pressure: Int,
    @ColumnInfo(name = "humidity") var humidity: Double,
    @ColumnInfo(name = "rain") var rain: Double
)