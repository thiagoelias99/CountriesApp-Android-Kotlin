package com.puc.telias.countriesapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityMainBinding
import com.puc.telias.countriesapp.databinding.AddCountryDialogBinding
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var loggedUser: String
    var searchedCountries: List<Country?> = emptyList()
    var selectedCountry: Country? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(this).countriesDao(),
            CountryClient()
        )
    }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = ""
        setSupportActionBar(binding.toolBar)

        //Ok
        loadUser()
        loadCountriesList(loggedUser)
        configureFAB(this)
        configureRecyclerView()

        //Tests
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                handleLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleLogout() {
        getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE).let { sharedPrefs ->
            val editor = sharedPrefs.edit()
            editor.apply {
                putString("USER_KEY", null)
            }.apply()
            Intent(this, LoginActivity::class.java).run {
                startActivity(this)
            }
        }
    }

    //Aux Functions
    private fun configureRecyclerView() {
        countriesListAdapter.itemClickHandler = {
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
    }

    private fun loadCountriesList(loggedUser: String) {
        lifecycleScope.launch {
            repository.getAllFromUser(loggedUser).collect {
                countriesListAdapter.update(it)
            }
        }
    }

    private fun loadUser() {
        val user: String? = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE).run {
            getString("USER_KEY", null)
        }
        if (user == null) {
            loggedUser = ""
            Intent(this, LoginActivity::class.java).run {
                startActivity(this)
            }
        } else {
            loggedUser = user
        }
    }

    private fun configureFAB(context: Context) {
        binding.fab.run {
            setOnClickListener {
                showDialog(context)
            }
        }
    }

    private fun showDialog(context: Context) {
        val addCountryDialogBinding = AddCountryDialogBinding.inflate(layoutInflater)
        var name = ""
        searchedCountries = emptyList()

        val alertDialog = AlertDialog.Builder(context)
            .setView(addCountryDialogBinding.root)
            .setPositiveButton("Confirmar") { _, _ -> positiveButtonHandler() }
            .setNegativeButton("Cancelar") { _, _ -> }
            .create()

        countriesSearchAdapter.itemClickHandler = {
            selectedCountry = it
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = true
        }

        val searchField = addCountryDialogBinding.searchText
        searchField.setText("")

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

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
                runnable?.let { handler.removeCallbacks(it) }
            }

            override fun afterTextChanged(s: Editable?) {
                runnable = Runnable {
                    name = searchField.text.toString()
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

    private fun positiveButtonHandler() {
        lifecycleScope.launch {
            selectedCountry?.let { country ->
                repository.save(country, loggedUser)
            }
        }
    }
}