package ru.nikol.simplyweather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.openweathermap.org/"
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}