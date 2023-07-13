package com.example.studex.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class Utils {
    companion object{

        fun printMsg(msg: String) {
            println("Studex_utils--> $msg")
        }

        fun showToast(context: Context?, msg : String){
            val toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 180)
            toast.show()
        }
    }
}