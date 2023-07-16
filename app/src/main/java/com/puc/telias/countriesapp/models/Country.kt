package com.puc.telias.countriesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey val uuid: UUID,
    @ColumnInfo("code_cca2") val code: String,
    @ColumnInfo("name_portuguese") val namePortuguese: String,
    @ColumnInfo("name_us") val nameUS: String,
    @ColumnInfo("name_local") val nameLocal: String,
    @ColumnInfo("name_complete") val nameComplete: String,
    val currency: String,
    val capital: String,
    val region: String,
    val languages: String,
    val area: Double,
    val population: Double,
    val flag: String,
    val history: String,
    @ColumnInfo("coat_of_arms") val coatOfArms: String,
    @ColumnInfo("user_name") var userName: String? = null,
    val latitude: Double,
    val longitude: Double,
)