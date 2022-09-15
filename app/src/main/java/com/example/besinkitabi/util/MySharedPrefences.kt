package com.example.besinkitabi.util
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class MySharedPrefences {

    companion object{

        private var TIME = "zaman"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : MySharedPrefences? = null
        private val lock = Any()
        operator fun invoke(context: Context): MySharedPrefences = instance ?: synchronized(lock){
                instance ?: makeMySharedPrefences(context).also {
                    instance = it
                }
        }
        private fun makeMySharedPrefences(context: Context) : MySharedPrefences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return  MySharedPrefences()
        }

    }
    fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }
    fun getTime() = sharedPreferences?.getLong(TIME,0)


}