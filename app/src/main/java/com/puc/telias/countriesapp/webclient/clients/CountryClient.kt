package com.puc.telias.countriesapp.webclient.clients

import android.util.Log
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

    suspend fun searchByCode(code: String): Country? {
        val country = countryServices.searchByCode(code).body()?.get(0)
        val countryIBGE = countryIBGEServices.searchByCode(code).body()?.get(0)

        return Country(
            code = country?.cca2 ?: "",
            namePortuguese = country?.translations?.get("por")?.common ?: "",
            nameUS = country?.name?.common ?: "",
            nameLocal = country?.name?.nativeName?.firstNotNullOf { it.value.common } ?: "",
            nameComplete = country?.name?.official ?: "",
            currency = country?.currencies?.firstNotNullOf { it.value.symbol } ?: "",
            capital = countryIBGE?.governo?.capital?.nome ?: "",
            region = country?.subregion ?: "",
            languages = countryIBGE?.linguas?.get(0)?.nome ?: "",
            area = countryIBGE?.area?.total?.replace(",",".")?.toDouble() ?: 0.0,
            population = country?.population ?: 0.0,
            flag = country?.flags?.png ?: "",
            coatOfArms = country?.coatOfArms?.png ?: "",
            uuid = UUID.randomUUID(),
            history = countryIBGE?.historico ?: "",
            latitude = country?.capitalInfo?.latlng?.get(0) ?: 0.0,
            longitude = country?.capitalInfo?.latlng?.get(1) ?: 0.0
        )
    }
}