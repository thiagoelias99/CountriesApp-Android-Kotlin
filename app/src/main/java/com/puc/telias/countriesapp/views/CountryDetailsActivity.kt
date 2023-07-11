package com.puc.telias.countriesapp.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityCountryDetailsBinding
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class CountryDetailsActivity : AppCompatActivity() {
    private val TAG = "CountryDetailsActivity"

    private lateinit var country: Country

    private val binding by lazy {
        ActivityCountryDetailsBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(this).countriesDao(),
            CountryClient()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val countryCode = intent.getStringExtra("COUNTRY_CODE")

        val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.apply {
            putString("COUNTRY_CODE", countryCode)
        }.apply()

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        lifecycleScope.launch {
            repository.getByCode(countryCode ?: "")?.let {
                country = it
                binding.flagContainer.load(it.flag)
            }
        }

//        binding.floatingActionButton.setOnClickListener {
//            lifecycleScope.launch {
//                country?.let {
//                    repository.destroy(it)
//                }
//                finish()
//            }
//        }
    }
}