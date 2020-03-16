package ru.nikol.simplyweather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nikol.simplyweather.db.WeatherDB
import ru.nikol.simplyweather.db.WeatherEntity
import ru.nikol.simplyweather.network.APIService
import ru.nikol.simplyweather.network.CurrentWeather
import ru.nikol.simplyweather.network.RetrofitClientInstance


class OverviewViewModel : ViewModel() {
    var city: MutableLiveData<String> = MutableLiveData<String>()
    var degrees: MutableLiveData<String> = MutableLiveData<String>()
    var windSize: MutableLiveData<String> = MutableLiveData<String>()
    var windDegrees: MutableLiveData<String> = MutableLiveData<String>()
    var pressureSize: MutableLiveData<String> = MutableLiveData<String>()
    var humidity: MutableLiveData<String> = MutableLiveData<String>()
    var rain: MutableLiveData<String> = MutableLiveData<String>()
    var image: MutableLiveData<Int> = MutableLiveData<Int>()
    lateinit var db: WeatherDB
    var apiService: APIService =
        RetrofitClientInstance.retrofitInstance!!.create(APIService::class.java)

    fun getWeather() {
        apiService.loadWeather()?.enqueue(object : Callback<CurrentWeather?> {
            override fun onFailure(call: Call<CurrentWeather?>, t: Throwable) {
                Log.d("retrofit2 failure", t.cause.toString())
            }

            override fun onResponse(
                call: Call<CurrentWeather?>,
                response: Response<CurrentWeather?>
            ) {
                city.value = response.body()?.name.toString()
                degrees.value =
                    "${(response.body()?.mainInfo?.temp?.minus(273)?.toInt()).toString()}°"
                windSize.value =
                    "${response.body()?.wind?.speed?.toInt().toString()} м/с, ${getWindDir(response.body()?.wind?.degrees!!)}"
                pressureSize.value =
                    "${((response.body()?.mainInfo?.pressure!!) / 1.333224).toInt()} мм.рт.ст"
                windDegrees.value = response.body()?.wind?.degrees.toString()
                humidity.value = "${response.body()?.mainInfo?.humidity?.toInt().toString()}%"
                rain.value = "${response.body()?.clouds?.all?.toInt().toString()}%"

                changeCurrentIcon(response.body()!!.weather[0].id)
                Log.d("icon", image.value.toString())

                Log.d("retrofit2 onResponse", "${response.body()}")
                val weatherEnt = WeatherEntity(
                    id = 0,
                    city = city.value!!,
                    temp = response.body()?.mainInfo?.temp?.toInt()!! - 273,
                    windSpeed = response.body()?.wind?.speed!!,
                    windDegrees = response.body()?.wind?.degrees!!,
                    pressure = ((response.body()?.mainInfo?.pressure!!) / 1.333224).toInt(),
                    humidity = response.body()?.mainInfo?.humidity!!,
                    rain = response.body()?.clouds?.all!!,
                    img = image.value!!
                )
                GlobalScope.launch {
                    db.weatherDao().insertAll(weatherEnt)
                    Log.d("Room object", "network value saved to bd: $weatherEnt")
                    Log.d("Room icon", image.value.toString() + " - image in room db")
                }

            }

        })
    }

    fun changeCurrentIcon(code: Int) {
        when (code) {
            in 200..232 -> {
                image.value = R.drawable.strom
            }
            in 300..321 -> {
                image.value = R.drawable.rain
            }

            in 500..531 -> {
                image.value = R.drawable.rain
            }
            in 700..781 -> {
                image.value = R.drawable.cloud
            }
            800 -> {
                image.value = R.drawable.sun
            }
            in 801..802 -> {
                image.value = R.drawable.partlyloudy
            }
            in 803..804 -> {
                image.value = R.drawable.cloud
            }
            else -> {
                image.value = R.drawable.partlyloudy
            }
        }
    }

    fun getWindDir(degrees: Int): String {
        return when (degrees) {
            in 0..22 -> "северный"
            in 23..67 -> "северо-восточный"
            in 68..113 -> "восточный"
            in 114..158 -> "южно-восточный"
            in 159..203 -> "южный"
            in 204..248 -> "юго-западный"
            in 248..293 -> "западный"
            in 294..338 -> "северо-западный"
            in 339..360 -> "северный"
            else -> ""
        }
    }

    fun getCashedWeather() {
        GlobalScope.launch {
            val data = db.weatherDao().getLast()
            if (data != (null)) {
                data.let {
                    city.postValue(it.city)
                    humidity.postValue("${it.humidity.toInt()}%")
                    pressureSize.postValue("${it.pressure} мм.рт.ст.")
                    rain.postValue("${it.rain.toInt()}%")
                    degrees.postValue("${it.temp}°")
                    windDegrees.postValue(getWindDir(it.windDegrees))
                    windSize.postValue("${it.windSpeed.toInt()} м/с, ${getWindDir(it.windDegrees)}")
                    image.postValue(it.img)
                }
                Log.d("Room", "Test cashed values: ${data.temp}")

            }

        }
    }

}
