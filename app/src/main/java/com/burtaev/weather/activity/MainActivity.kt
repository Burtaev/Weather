package com.burtaev.weather.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.burtaev.weather.R
import com.burtaev.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.appBarMain.toolbar
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_weather
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }
}
