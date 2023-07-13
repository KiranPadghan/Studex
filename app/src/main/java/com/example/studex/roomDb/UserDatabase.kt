package com.example.studex.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.studex.roomDb.Daos.StudentDao
import com.example.studex.roomDb.Daos.UserDao
import com.example.studex.roomDb.entities.Student
import com.example.studex.roomDb.entities.User
import com.example.studex.roomDb.typeConverters.TypeConverter

@Database(entities = [
    User::class,
    Student::class
    ], version = 1)
@TypeConverters(TypeConverter::class)
abstract class UserDatabase : RoomDatabase() {

    companion object {
        val USER_DATABASE = "USER_DATABASE"
    }

    abstract val userDao : UserDao

    abstract val studentDao : StudentDao

}