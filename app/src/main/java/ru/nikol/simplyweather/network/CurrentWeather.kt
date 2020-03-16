package ru.nikol.simplyweather.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeather(

    @SerializedName("coord")
    @Expose
    val coord: Coords,

    @SerializedName("weather")
    @Expose
    val weather: MutableList<Weather>,

    @SerializedName("main")
    @Expose
    val mainInfo: MainInf,

    @SerializedName("clouds")
    @Expose
    val clouds: Clouds,

    @SerializedName("sys")
    @Expose
    val sys: Sys,

    @SerializedName("wind")
    @Expose
    val wind: Wind,

    @SerializedName("base")
    @Expose
    val base: String = "",

    @SerializedName("visibility")
    @Expose
    val visibility: Int,

    @SerializedName("dt")
    @Expose
    val dt: Long,

    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("cod")
    @Expose
    val cod: Int
)

data class Coords(
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("userId")
    @Expose
    val lat: Double
)

data class Weather(
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("main")
    @Expose
    val mainInfo: String = "",

    @SerializedName("description")
    @Expose
    val description: String = "",

    @SerializedName("icon")
    @Expose
    val icon: String = ""
)

data class MainInf(
    @SerializedName("temp")
    @Expose
    val temp: Double,

    @SerializedName("feels_like")
    @Expose
    val tempFeelsLike: Double,

    @SerializedName("temp_min")
    @Expose
    val tempMin: Double,

    @SerializedName("temp_max")
    @Expose
    val tempMax: Double,

    @SerializedName("pressure")
    @Expose
    val pressure: Int,

    @SerializedName("humidity")
    @Expose
    val humidity: Double
)

data class Wind(
    @SerializedName("speed")
    @Expose
    val speed: Double,

    @SerializedName("deg")
    @Expose
    val degrees: Int
)

data class Clouds(
    @SerializedName("all")
    @Expose
    val all: Double
)

data class Sys(
    @SerializedName("type")
    @Expose
    val type: Int,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("message")
    @Expose
    val message: Double,

    @SerializedName("country")
    @Expose
    val country: String = "",

    @SerializedName("sunrise")
    @Expose
    val sunrise: Long,

    @SerializedName("sunset")
    @Expose
    val sunset: Long
)
