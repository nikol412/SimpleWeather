package ru.nikol.simplyweather

import android.util.Log
import androidx.room.Room

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.nikol.simplyweather", appContext.packageName)
    }

    @RunWith(AndroidJUnit4::class)
    class EnityReadWriteTest {
        private lateinit var weatherDao: WeatherDAO
        private lateinit var db: WeatherDB

        @Before
        fun createDb() {
            val context = InstrumentationRegistry.getInstrumentation().context
            db = Room.inMemoryDatabaseBuilder(
                context, WeatherDB::class.java).build()
            weatherDao = db.weatherDao()
        }

        @After
        @Throws(IOException::class)
        fun closeDb() {
            db.close()
        }

        @Test
        @Throws(Exception::class)
        fun writeUserAndReadInList() {
            val weatherEntity: WeatherEntity = WeatherEntity(city = "Omsk", humidity = 1.0, id = 1, pressure = 1, rain = 2.0, windDegrees = 3, windSpeed = 4.1, temp = 432)
            weatherDao.insertAll(weatherEntity)
            val weatherItem = weatherDao.findByCity("Omsk")
            //assertThat(arrayListOf(weatherItem), equalTo(weatherEntity))
            Log.d("Room", "${weatherItem[0].temp} cool")
        }
    }
}
