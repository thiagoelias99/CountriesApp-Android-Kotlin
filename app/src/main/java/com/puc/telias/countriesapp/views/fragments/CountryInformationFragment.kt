package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.FragmentCountryInformationBinding
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class CountryInformationFragment : Fragment() {
    private lateinit var binding: FragmentCountryInformationBinding
    private lateinit var activityContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    private val repository by lazy {
        CountriesRepository(
            AppDatabase.getConnection(activityContext).countriesDao(),
            CountryClient()
        )
    }

    private var country: Country? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryCode = "BR"

//        val countryCode = intent.getStringExtra("COUNTRY_CODE")

//        binding.floatingActionButton.setOnClickListener {
//            lifecycleScope.launch {
//                country?.let {
//                    repository.destroy(it)
//                }
//                finish()
//            }
//        }

        lifecycleScope.launch {
            country = repository.getByCode(countryCode ?: "")

            binding.title.text = country?.namePortuguese ?: ""
            binding.subTitle.text = country?.nameUS ?: ""
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