package com.example.studex.modules.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studex.modules.repository.UserRepository
import com.example.studex.roomDb.entities.Student
import com.example.studex.roomDb.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var saveUserResponse : MutableLiveData<Long> = MutableLiveData()
    var loginSuccessResponse : MutableLiveData<Boolean> = MutableLiveData()

    fun saveUser(user : User) {
        CoroutineScope(Dispatchers.IO).launch {
            saveUserResponse.postValue(userRepository.saveUser(user))
        }
    }

    fun doLogin(username: String, password : String) {
        CoroutineScope(Dispatchers.IO).launch {
            loginSuccessResponse.postValue(userRepository.validateLogin(username,password))
        }
    }

}