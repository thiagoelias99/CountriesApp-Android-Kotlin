package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {
    private lateinit var activityContext: Context
    private var country: Country? = null

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

    private val callback = OnMapReadyCallback { googleMap ->
        val sharedPrefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val countryCode = sharedPrefs.getString("COUNTRY_CODE", null)

        lifecycleScope.launch {
            country = repository.getByCode(countryCode ?: "")

            val countryLocation = LatLng(country?.latitude ?: 0.0, country?.longitude ?: 0.0)
            googleMap.addMarker(MarkerOptions().position(countryLocation).title(country?.namePortuguese ?: ""))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(countryLocation))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}