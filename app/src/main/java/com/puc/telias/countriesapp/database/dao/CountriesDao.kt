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
}