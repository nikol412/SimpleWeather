package ru.nikol.simplyweather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
             }

         })
    }
}
