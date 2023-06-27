package com.puc.telias.countriesapp.repository

import com.puc.telias.countriesapp.database.dao.CountriesDao
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.flow.Flow

class CountriesRepository(
    private val dao: CountriesDao,
    private val webClient: CountryClient
) {
    private val TAG = "CountriesRepository"

    suspend fun search(search: String): List<Country?>?{
        return webClient.searchByName(search)
    }

    suspend fun save(country: Country){
        return dao.insert(country)
    }

    fun getAll(): Flow<List<Country>>{
        return dao.getAll()
    }
    suspend fun getByCode(code: String): Country?{
        return dao.getByCode(code)
    }
}