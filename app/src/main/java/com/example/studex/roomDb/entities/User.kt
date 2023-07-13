package com.example.studex.roomDb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") var userId: Long,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "user_first_name") var userFirstName: String,
    @ColumnInfo(name = "user_password") var userPassword: String,
)
