package com.puc.telias.countriesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(
    @PrimaryKey @ColumnInfo("user_name") val userName: String,
    val name: String,
    val password: String
)