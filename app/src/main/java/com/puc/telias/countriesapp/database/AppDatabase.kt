package com.puc.telias.countriesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.puc.telias.countriesapp.database.dao.CountriesDao
import com.puc.telias.countriesapp.database.dao.UsersDao
import com.puc.telias.countriesapp.models.Country
import com.puc.telias.countriesapp.models.User

@Database(entities = [Country::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile private var db: AppDatabase? = null
        fun getConnection(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "countries.db"
            )
                .allowMainThreadQueries()
                .build().also {
                    db = it
                }
        }
    }
}