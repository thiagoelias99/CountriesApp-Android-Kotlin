package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.FragmentCountryHistoryBinding
import com.puc.telias.countriesapp.databinding.FragmentCountryInformationBinding
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class CountryHistoryFragment : Fragment() {
    private lateinit var binding: FragmentCountryHistoryBinding
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
        binding = FragmentCountryHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val countryCode = sharedPrefs.getString("COUNTRY_CODE", null)

        lifecycleScope.launch {
            country = repository.getByCode(countryCode ?: "")

            binding.history.text = country?.history
        }
    }
}