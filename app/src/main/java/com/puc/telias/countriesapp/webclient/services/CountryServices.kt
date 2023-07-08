package com.puc.telias.countriesapp.webclient.services

import com.puc.telias.countriesapp.webclient.models.CountryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryServices {
    @GET("translation/{search}")
    suspend fun searchByName(@Path("search") userLogin: String): Response<List<CountryResponse>>

    @GET("alpha/{code}")
    suspend fun searchByCode(@Path("code") userLogin: String): Response<List<CountryResponse>>
}