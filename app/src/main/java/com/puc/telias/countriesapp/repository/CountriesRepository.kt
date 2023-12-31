package com.puc.telias.countriesapp.repository

import android.util.Log
import com.puc.telias.countriesapp.database.dao.CountriesDao
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.flow.Flow

class CountriesRepository(
    private val dao: CountriesDao,
    private val webClient: CountryClient
) {
    private val TAG = "CountriesRepository"

    suspend fun search(search: String): List<Country?>? {
        val regex = Regex(search, RegexOption.IGNORE_CASE)
        var filteredCountries: List<Country?>? = null

        webClient.searchByName(search)?.let { countries ->
//            filteredCountries = countries.filter { it?.namePortuguese?.contains(regex) ?: false }
            filteredCountries = countries.filter { it?.namePortuguese?.startsWith(search,true) ?: false }
        }

        return filteredCountries
    }

    suspend fun save(country: Country, userName: String) {
        val updatedCountry = reFetchData(country?.code ?: "")
        updatedCountry?.let {
            it.userName = userName
            dao.insert(it)
        }
        return
    }

    fun getAll(): Flow<List<Country>> {
        return dao.getAll()
    }

    fun getAllFromUser(userName: String): Flow<List<Country>> {
        return dao.getAllFromUser(userName)
    }

    suspend fun getByCode(code: String): Country? {
        return dao.getByCode(code)
    }

    suspend fun destroy(country: Country) {
        return dao.destroy(country)
    }

    suspend fun reFetchData(code: String): Country?{
        return webClient.searchByCode(code)
    }
}