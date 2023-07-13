package com.example.studex.roomDb.Daos

import androidx.room.*
import com.example.studex.roomDb.entities.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student): Long

    @Query("SELECT * FROM STUDENT")
    suspend fun getStudentList() : List<Student>

    @Query("DELETE FROM STUDENT WHERE student_id = :id")
    suspend fun deleteStudentById(id : Long) : Int
}