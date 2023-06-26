package com.puc.telias.countriesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.puc.telias.countriesapp.database.dao.CountriesDao
import com.puc.telias.countriesapp.models.Country

@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gitHubUserDao(): CountriesDao

    companion object {
        @Volatile private var db: AppDatabase? = null
        fun getConnection(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "weatherapp.db"
            )
                .allowMainThreadQueries()
                .build().also {
                    db = it
                }
        }
    }
}