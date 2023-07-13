package com.example.studex.modules.loginModule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.studex.R
import com.example.studex.databinding.ActivityStudentBinding
import com.example.studex.modules.homeModule.ui.AddStudentFragment
import com.example.studex.modules.homeModule.ui.StudentListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragmentToBackStack(StudentListFragment())
    }

    fun addFragmentToBackStack(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Student_activity,fragment,fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName).commit()
    }

    override fun onBackPressed() {
        val fl = supportFragmentManager.findFragmentById(R.id.fl_Student_activity)
        if (fl == null){
            this.finish()
        }
        fl?.let {
            when(it.javaClass.simpleName) {
                AddStudentFragment::class.java.simpleName -> {
                    supportFragmentManager.popBackStack()
                }
                StudentListFragment::class.java.simpleName -> {
                    this.finish()
                }
                else -> {
                    super.onBackPressed()
                }
            }
        }
    }
}