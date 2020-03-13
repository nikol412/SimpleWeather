package ru.nikol.simplyweather

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OverviewViewModel : ViewModel() {
    var city = "Омск"
    var degrees = ""
    var windSize = "123 м/с, эльфийский"
    var pressureSize = "302 мм.рт.ст."
    var humidity = "-20%"
    var rain = "120%"
//    private val weatherAPI by lazy {
//        WeatherAPI.create()
//    }
    init {

    }

    fun getWeather(){
        val BASE_URL = "https://samples.openweathermap.org/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         retrofit.create(APIService::class.java).loadWeather()?.enqueue(object: Callback<CurrentWeather?>{
             override fun onFailure(call: Call<CurrentWeather?>, t: Throwable) {
                 Log.d("retrofit2 failure", t.cause.toString())
             }

             override fun onResponse(
                 call: Call<CurrentWeather?>,
                 response: Response<CurrentWeather?>
             ) {

                 Log.d("retrofit2 onResponse", "${response.body()?.wind} \n ${response.body()?.mainInfo?.temp}\"")
             }

         })
    }
}
