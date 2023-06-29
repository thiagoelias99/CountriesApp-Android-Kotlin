package com.puc.telias.countriesapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityMainBinding
import com.puc.telias.countriesapp.databinding.AddCountryDialogBinding
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"


    var loggedUser: String? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(this).countriesDao(),
            CountryClient()
        )
    }

    var searchedCountries: List<Country?> = emptyList()
    var selectedCountry: Country? = null

    private val countriesSearchAdapter by lazy {
        CountriesSearchAdapter(
            context = this,
            countriesList = searchedCountries
        )
    }

    private val countriesListAdapter by lazy {
        CountriesListAdapter(
            context = this,
            countriesList = emptyList()
        )
    }

    private fun onClick(country: Country) {
        selectedCountry = country
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        loggedUser = sharedPrefs.getString("USER_KEY", null)

        if (loggedUser.isNullOrEmpty()) {
            Intent(this, LoginActivity::class.java).run {
                startActivity(this)
            }
        } else {
            lifecycleScope.launch {
                repository.getAllFromUser(loggedUser!!).collect {
                    Log.i(TAG, "carregados: $it")
                    countriesListAdapter.update(it)
                }
            }
        }


        val fab = binding.fab

        countriesListAdapter.quandoClicaNoItem = {
            Intent(this, CountryDetailsActivity::class.java).run {
                putExtra("COUNTRY_CODE", it.code)
                startActivity(this)
            }
        }
        val recyclerList = binding.recyclerView
        recyclerList.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countriesListAdapter
        }


        fab.setOnClickListener {
            val addCountryDialogBinding = AddCountryDialogBinding.inflate(layoutInflater)
            var name = ""

            val alertDialog = AlertDialog.Builder(this)
                .setView(addCountryDialogBinding.root)
                .setPositiveButton("Confirmar") { _, _ ->
                    lifecycleScope.launch {
                        selectedCountry?.let { country ->
                            country.userName = loggedUser
                            repository.save(country)
                        }
                    }
                }
                .setNegativeButton("Cancelar") { _, _ ->

                }
                .create()

            countriesSearchAdapter.quandoClicaNoItem = {
                selectedCountry = it
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = true
            }

            val searchField = addCountryDialogBinding.searchText

            searchField.addTextChangedListener(object : TextWatcher {
                private val handler = Handler()
                private var runnable: Runnable? = null
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
                    runnable?.let { handler.removeCallbacks(it) }
                }

                override fun afterTextChanged(s: Editable?) {
                    runnable = Runnable {
                        name = searchField.text.toString()
                        Log.i(TAG, "afterTextChanged: $name")
                        lifecycleScope.launch {
                            searchedCountries = repository.search(name) ?: emptyList()
                            Log.i(TAG, "onCreate: $searchedCountries")
                            countriesSearchAdapter.update(searchedCountries)
                        }
                    }
                    handler.postDelayed(runnable!!, 300) //
                }
            })


            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
            }

            addCountryDialogBinding.recyclerView.run {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = countriesSearchAdapter
            }
            alertDialog.show()
        }
    }
}