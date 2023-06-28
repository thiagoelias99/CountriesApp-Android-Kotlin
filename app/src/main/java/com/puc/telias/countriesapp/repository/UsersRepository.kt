package com.puc.telias.countriesapp.repository

import com.puc.telias.countriesapp.database.dao.CountriesDao
import com.puc.telias.countriesapp.database.dao.UsersDao
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.models.User
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.flow.Flow

class UsersRepository(
    private val dao: UsersDao,
) {
    private val TAG = "UsersRepository"

//    suspend fun search(search: String): List<Country?>?{
//        return webClient.searchByName(search)
//    }

    suspend fun save(user: User){
        return dao.insert(user)
    }

    suspend fun login(userName: String, password: String): User?{
        dao.selectByUserName(userName)?.let { user ->
            if (user.password == password) return user
        }
        return null
    }

//    fun getAll(): Flow<List<Country>>{
//        return dao.getAll()
//    }
//    suspend fun getByCode(code: String): Country?{
//        return dao.getByCode(code)
//    }
//
//    suspend fun destroy(country: Country){
//        return dao.destroy(country)
//    }
}