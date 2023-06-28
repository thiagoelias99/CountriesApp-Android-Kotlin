package com.puc.telias.countriesapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("""SELECT * FROM user WHERE user_name = :userName""")
    suspend fun selectByUserName(userName: String): User?

//    @Delete
//    suspend fun destroy(user: User)

//    @Query("""SELECT * FROM countries WHERE code_cca3 = :code""")
//    suspend fun getByCode(code: String): Country?
}