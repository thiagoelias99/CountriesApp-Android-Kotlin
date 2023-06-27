package com.puc.telias.countriesapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityCountryDetailsBinding
import com.puc.telias.countriesapp.databinding.ActivityMainBinding
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class CountryDetailsActivity : AppCompatActivity() {
    private val TAG = "CountryDetailsActivity"

    private val binding by lazy {
        ActivityCountryDetailsBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(this).gitHubUserDao(),
            CountryClient()
        )
    }

    private var country: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val countryCode = intent.getStringExtra("COUNTRY_CODE")

        binding.textView.text = countryCode
        lifecycleScope.launch {
            country = repository.getByCode(countryCode ?: "")
            Log.i(TAG, "onCreate: $country")
        }

    }
}