package com.puc.telias.countriesapp.webclient.services

import com.puc.telias.countriesapp.webclient.models.CountryResponse
import com.puc.telias.countriesapp.webclient.models.CountryResponseIBGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryIBGEServices {
    @GET("{search}")
    suspend fun searchByCode(@Path("search") code: String): Response<List<CountryResponseIBGE>>
}