package com.puc.telias.countriesapp.webclient

import com.puc.telias.countriesapp.webclient.services.CountryServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitConfig {
    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/translation/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    val countryServices = retrofit.create(CountryServices::class.java)
}