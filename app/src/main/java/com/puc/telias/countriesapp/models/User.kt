package com.puc.telias.countriesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(
    @PrimaryKey val uuid: UUID,
    val name: String,
    @ColumnInfo("user_name") val userName: String,
    val password: String
)