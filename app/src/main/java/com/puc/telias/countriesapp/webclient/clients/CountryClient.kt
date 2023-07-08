package com.puc.telias.countriesapp.webclient.clients

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.webclient.RetrofitConfig
import com.puc.telias.countriesapp.webclient.services.CountryIBGEServices
import com.puc.telias.countriesapp.webclient.services.CountryServices
import java.util.UUID

class CountryClient {
    private val TAG = "CountryClient"
    private val countryServices: CountryServices = RetrofitConfig().countryServices
    private val countryIBGEServices: CountryIBGEServices = RetrofitConfig().countryIBGEServices

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

    suspend fun searchByCode() {
        val code = "BR"
        val countryIBGE = countryIBGEServices.searchByCode(code).body()?.map { it.country }?.get(0)
        val country = countryServices.searchByCode(code).body()?.map { it.country }?.get(0)

        val country2: Country = Country(
            uuid = country.uuid,
            code = String,
            namePortuguese =: String,
            nameUS = String,
            nameLocal = String,
            nameComplete = String,
            currency = String,
            capital = String,
            region = String,
            languages = String,
            area = Double,
            population = Double,
            flag = String,
            coatOfArms = String,
            userName = String? = null
        )


        Log.i(TAG, "searchByCode: $countryIBGE")
        Log.i(TAG, "searchByCode: $country")


    }
}