package com.puc.telias.countriesapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import coil.load
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityCountryDetailsBinding
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
            AppDatabase.getConnection(this).countriesDao(),
            CountryClient()
        )
    }

    private var country: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val countryCode = intent.getStringExtra("COUNTRY_CODE")

        binding.floatingActionButton.setOnClickListener {
            lifecycleScope.launch {
                country?.let {
                    repository.destroy(it)
                }
                finish()
            }
        }

        lifecycleScope.launch {
            country = repository.getByCode(countryCode ?: "")
            Log.i(TAG, "onCreate: $country")

            binding.title.text = country?.nameUS ?: ""
            binding.subTitle.text = country?.namePortuguese ?: ""
            binding.decription.text = country?.nameComplete ?: ""
            binding.capital.text = country?.capital ?: ""
            binding.currency.text = country?.currency ?: ""
            binding.area.text = country?.area.toString() ?: ""
            binding.population.text = country?.population.toString() ?: ""
            binding.flagContainer.load(country?.flag)
            binding.armsContainer.load(country?.coatOfArms)
        }
    }
}