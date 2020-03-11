package ru.nikol.simplyweather

import androidx.lifecycle.ViewModel

class OverviewViewModel : ViewModel() {
    var city = "Омск"
    var degrees = "+27"
    var windSize = "123 м/с, эльфийский"
    var pressureSize = "302 мм.рт.ст."
    var humidity = "-20%"
    var rain = "120%"
    init {

    }
}
