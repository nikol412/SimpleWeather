package ru.nikol.simplyweather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OverviewViewModel : ViewModel() {
    var city:MutableLiveData<String> = MutableLiveData<String>()
    var degrees:MutableLiveData<String> = MutableLiveData<String>()
    var windSize: MutableLiveData<String> = MutableLiveData<String>()
    var pressureSize: MutableLiveData<String> = MutableLiveData<String>()
    var humidity : MutableLiveData<String> = MutableLiveData<String>()
    var rain : MutableLiveData<String> = MutableLiveData<String>()

    lateinit var db: WeatherDB



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
                 city.value = response.body()?.name.toString()
                 degrees.value = "${(response.body()?.mainInfo?.temp?.minus(273)?.toInt()).toString()}°"
                 windSize.value =  "${response.body()?.wind?.speed.toString()} м/с, ${response.body()?.wind?.degrees.toString()}"
                 pressureSize.value = "${response.body()?.mainInfo?.pressure.toString()} мм.рт.ст"
                 humidity.value = "${response.body()?.mainInfo?.humidity?.toInt().toString()}%"
                 rain.value = "${response.body()?.clouds?.all.toString()}%"

                 Log.d("retrofit2 onResponse", "${response.body()}")

                 val weatherEnt = WeatherEntity(id = 0, city = response.body()?.name.toString(),
                     temp = response.body()?.mainInfo?.temp?.minus(273)?.toInt()!!, windSpeed = response.body()?.wind?.speed!!,
                     windDegrees = response.body()?.wind?.degrees!!, pressure = response.body()?.mainInfo?.pressure!!, humidity = response.body()?.mainInfo?.humidity!!,
                     rain = response.body()?.clouds?.all!!)
                 var testEntity:WeatherEntity
                 GlobalScope.launch {
                     db.weatherDao().insertAll(weatherEnt)
                     testEntity = db.weatherDao().getLast()
                     Log.d("Room", "Test: ${testEntity.temp} ${testEntity.city} ${testEntity.humidity}")
                 }

             }

         })
    }

    fun getCashedWeather(context: Context) {
        GlobalScope.launch {
            val data = db.weatherDao().getLast()
            if(data != (null)) {
                data.let {
                    city.postValue(it.city)
                    humidity.postValue(it.humidity.toString())
                    pressureSize.postValue(it.pressure.toString())
                    rain.postValue(it.rain.toString())
                    degrees.postValue(it.temp.toString())
                    degrees.postValue(it.windDegrees.toString())
                    windSize.postValue(it.windSpeed.toString())
                }
                    var testEntity = db.weatherDao().getLast()
                    Log.d("Room", "Test: ${testEntity.temp} ${testEntity.city} ${testEntity.humidity}")

            }

        }
    }

}
