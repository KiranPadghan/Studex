package com.example.studex.modules.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studex.modules.repository.UserRepository
import com.example.studex.roomDb.entities.Student
import com.example.studex.utils.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var studentList : MutableLiveData<List<Student>> = MutableLiveData()
    var studentInsertResponse : MutableLiveData<Long> = MutableLiveData()
    var studentDeleteResponse : MutableLiveData<Int> = MutableLiveData()


    init {
        CoroutineScope(Dispatchers.IO).launch {
            studentList.postValue(userRepository.getStudentList())
        }
    }

    fun addStudentModel(student : Student) {
        CoroutineScope(Dispatchers.IO).launch {
            studentInsertResponse.postValue(userRepository.saveStudent(student))
        }
    }

    fun getStudentList(){
        CoroutineScope(Dispatchers.IO).launch {
            studentList.postValue(userRepository.getStudentList())
        }
    }

    fun deleteStudentById(studentId : Long){
        CoroutineScope(Dispatchers.IO).launch {
            studentDeleteResponse.postValue(userRepository.deleteStudentById(studentId))
        }
    }

}