package ru.nikol.simplyweather

import retrofit2.Call
import retrofit2.http.GET


interface APIService {
//    @GET("data/2.5/weather?id=1496153&appid=ae21efbdc75d07343cb2a27262ded06e")
//    fun loadWeather(): Call<CurrentWeather?>?
    //@GET("data/2.5/weather?q=Omsk&appid=ae21efbdc75d07343cb2a27262ded06e&lang=ru")

    @GET("data/2.5/weather?q=Omsk,ru&appid=ae21efbdc75d07343cb2a27262ded06e")
    fun loadWeather(): Call<CurrentWeather?>?
    //@GET("data/2.5/weather?q=Omsk&appid=ae21efbdc75d07343cb2a27262ded06e")
}