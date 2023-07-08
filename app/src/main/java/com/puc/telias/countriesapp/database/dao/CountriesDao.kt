package com.puc.telias.countriesapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puc.telias.countriesapp.models.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: Country)

    @Delete
    suspend fun destroy(country: Country)

    @Query("""SELECT * FROM countries""")
    fun getAll(): Flow<List<Country>>

    @Query("""SELECT * FROM countries WHERE user_name = :userName""")
    fun getAllFromUser(userName: String): Flow<List<Country>>

    @Query("""SELECT * FROM countries WHERE code_cca2 = :code""")
    suspend fun getByCode(code: String): Country?
}