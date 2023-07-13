package com.example.studex.modules.repository

import com.example.studex.roomDb.UserDatabase
import com.example.studex.roomDb.entities.Student
import com.example.studex.roomDb.entities.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDb : UserDatabase
) {

    suspend fun validateLogin(userName : String, password : String): Boolean {
        val response = userDb.userDao.getValidUser(userName,password)
        return (response != null)
    }

    suspend fun saveUser(user : User) :Long {
        return userDb.userDao.insert(user)
    }

    suspend fun getStudentList(): List<Student> {
        return userDb.studentDao.getStudentList()
    }

    suspend fun saveStudent(student : Student) :Long {
        return userDb.studentDao.insert(student)
    }

    suspend fun deleteStudentById(studentId : Long): Int  {
        return userDb.studentDao.deleteStudentById(studentId)
    }

}