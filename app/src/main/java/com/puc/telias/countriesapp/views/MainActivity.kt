package com.puc.telias.countriesapp.views

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

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(this).gitHubUserDao(),
            CountryClient()
        )
    }

    var countries: List<Country?> = emptyList()

    private val countriesAdapter by lazy {
        CountriesSearchAdapter(context = this, countriesList = countries)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fab = binding.fab

        fab.setOnClickListener {
            val addCountryDialogBinding = AddCountryDialogBinding.inflate(layoutInflater)

            var name = ""

            val alertDialog = AlertDialog.Builder(this)
                .setView(addCountryDialogBinding.root)
                .setPositiveButton("Confirmar") { _, _ ->
//                    lifecycleScope.launch {
//                        user?.let {user ->
//                            repository.addAdd(user)
//                        }
//                    }
                }
                .setNegativeButton("Cancelar") { _, _ ->

                }
                .create()

            val searchField = addCountryDialogBinding.searchText

            searchField.addTextChangedListener(object : TextWatcher {
                private val handler = Handler()
                private var runnable: Runnable? = null
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    runnable?.let { handler.removeCallbacks(it) }
                }

                override fun afterTextChanged(s: Editable?) {
                    runnable = Runnable {
                        name = searchField.text.toString()
                        Log.i(TAG, "afterTextChanged: $name")
                        lifecycleScope.launch {
                            countries = repository.search(name) ?: emptyList()
                            Log.i(TAG, "onCreate: $countries")
                            countriesAdapter.update(countries)
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
                adapter = countriesAdapter
            }

//            addCountryDialogBinding.addGitHubUserDialogButton.setOnClickListener {
//                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
//                lifecycleScope.launch {
//                    userLogin = addCountryDialogBinding.addGitHubUserDialogSearch.text.toString()
//                    user = repository.getUserByLogin(userLogin)
//                    user?.let {user ->
//                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = true
//                        addCountryDialogBinding.run {
//                            addGitHubUserDialogName.text = user.name
//                            addGitHubUserDialogCompany.text = user.company
//                        }
//                    }
//                }
//            }

            alertDialog.show()
        }
    }
}