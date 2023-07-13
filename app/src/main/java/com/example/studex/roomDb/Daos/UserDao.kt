package com.example.studex.roomDb.Daos

import androidx.room.*
import com.example.studex.roomDb.entities.Student
import com.example.studex.roomDb.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM USER WHERE user_name = :userName and user_password = :password limit 1")  //case
    suspend fun getValidUser(userName: String, password: String): User?
}