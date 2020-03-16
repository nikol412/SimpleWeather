package ru.nikol.simplyweather.network

import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("data/2.5/weather?q=Omsk,ru&appid=ae21efbdc75d07343cb2a27262ded06e")
    fun loadWeather(): Call<CurrentWeather?>?
}