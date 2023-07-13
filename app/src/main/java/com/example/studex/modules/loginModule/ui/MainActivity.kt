package com.example.studex.modules.loginModule.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.studex.R
import com.example.studex.databinding.ActivityMainBinding
import com.example.studex.utils.Constants
import com.example.studex.utils.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragmentToBackStack(LoginFragment())
        setScreenAsPerSession()
    }

    private fun setScreenAsPerSession() {
        if (SharedPreferences.getBoolean(this,Constants.IS_LOGGED_IN)){
            openStudentActivity()
        }
    }

    private fun openStudentActivity(){
        val intent = Intent(this.application, StudentActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    fun addFragmentToBackStack(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_login_activity,fragment,fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName).commit()
    }

    override fun onBackPressed() {
        val fl = supportFragmentManager.findFragmentById(R.id.fl_login_activity)
        if (fl == null){
            this.finish()
        }
        fl?.let {
            when(it.javaClass.simpleName) {
                RegisterUserFragment::class.java.simpleName -> {
                    supportFragmentManager.popBackStack()
                }
                LoginFragment::class.java.simpleName -> {
                    this.finish()
                }
                else -> {
                    super.onBackPressed()
                }
            }
        }
    }
}