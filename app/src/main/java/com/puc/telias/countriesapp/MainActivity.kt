package com.puc.telias.countriesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(this).gitHubUserDao(),
            CountryClient()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = "ukraine"

        lifecycleScope.launch {
            val countries = repository.search(name)
            Log.i(TAG, "onCreate: $countries")
        }
    }
}