package com.example.studex.utils

import android.app.Activity
import android.content.Context
import android.util.Log

object SharedPreferences {
    const val TAG = "StudentSharedPreference"

    fun getString(context: Context?, key: String?): String {
        var returnString = ""
        if (context != null) {
            val sp = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
            returnString = sp.getString(key, "").toString()
        }
        return returnString
    }

    fun setString(context: Context?, key: String?, value: String?) {
        if (context != null) {
            val sharedPreferences = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun setBoolean(context: Context?, key: String?, value: Boolean) {
        Log.e("SET $key", value.toString())
        if (context != null) {
            val sharedPreferences = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun getBoolean(context: Context?, key: String?): Boolean {
        var returnBoolean = false
        if (context != null) {
            val sp = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
            sp.getBoolean(key, false).also { returnBoolean = it }
        }
        Log.e("GET $key", returnBoolean.toString())
        return returnBoolean
    }
}