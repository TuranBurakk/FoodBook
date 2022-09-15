package com.example.besinkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinkitabi.model.Food
import com.example.besinkitabi.servis.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailsViewModel(application: Application): BaseViewModel(application) {
    val foodLiveData = MutableLiveData<Food>()
    fun getRoomData(uuid : Int){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData .value = food
        }

    }
}