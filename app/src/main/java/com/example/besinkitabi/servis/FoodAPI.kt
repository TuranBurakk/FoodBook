package com.example.besinkitabi.servis

import com.example.besinkitabi.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {

    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json


    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getFood() : Single<List<Food>>

}