package ru.nikol.simplyweather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "weather_items")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "temp") var temp: Int,
    @ColumnInfo(name = "windSpeed") var windSpeed: Double,
    @ColumnInfo(name = "windDegrees") var windDegrees: Int,
    @ColumnInfo(name = "pressure") var pressure: Int,
    @ColumnInfo(name = "humidity") var humidity: Double,
    @ColumnInfo(name = "rain") var rain: Double
)