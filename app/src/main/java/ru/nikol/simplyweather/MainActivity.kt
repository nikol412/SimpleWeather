package ru.nikol.simplyweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.placeholder, OverviewFragment())
//        transaction.commit()
    }
}
