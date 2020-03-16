package ru.nikol.simplyweather

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = arrayOf(WeatherEntity::class), version = 2, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO

    companion object {

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) { // Since we didn't alter the table, there's nothing else to do here.
                database.execSQL("ALTER TABLE weather_items "
                        + " ADD COLUMN image INTEGER");
            }
            }


        @Volatile private var instance: WeatherDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            WeatherDB::class.java, "weather-list.db")
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
}
