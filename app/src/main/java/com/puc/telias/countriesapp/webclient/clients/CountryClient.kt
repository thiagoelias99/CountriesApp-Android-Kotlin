package com.puc.telias.countriesapp.webclient.clients

import android.util.Log
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.webclient.RetrofitConfig
import com.puc.telias.countriesapp.webclient.services.CountryServices

class CountryClient {
    private val TAG = "CountryClient"
    private val countryServices: CountryServices = RetrofitConfig().countryServices

    suspend fun searchByName(search: String): List<Country?>? {
        return (
                try {
                    val response = countryServices.searchByName(search)
                    response.body()?.map { it.country }
                } catch (e: Exception) {
                    Log.e(TAG, "searchByName: ", e)
                    null
                }
                )
    }
}