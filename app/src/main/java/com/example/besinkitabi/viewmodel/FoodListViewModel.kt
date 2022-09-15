package com.example.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinkitabi.model.Food
import com.example.besinkitabi.servis.FoodAPIServis
import com.example.besinkitabi.servis.FoodDatabase
import com.example.besinkitabi.util.MySharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {
    val foods = MutableLiveData<List<Food>>()
    val foodMistake = MutableLiveData<Boolean>()
    val foodDownload = MutableLiveData<Boolean>()
    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val foodApiServis = FoodAPIServis()
    private val disposable = CompositeDisposable()
    private val MySharedPrefences = MySharedPrefences(getApplication())


    fun refreshData(){
        val savedTime = MySharedPrefences.getTime()
        if (savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < updateTime){
            getDataSqlite()
        }else {
            getDataFromNet()
        }
    }

    fun refreshFromNet(){
        getDataFromNet()
    }

    private  fun getDataSqlite(){
        foodDownload.value = true
        launch {
            val foodList =FoodDatabase(getApplication()).foodDao().getAllFood()
            showFood(foodList)
            Toast.makeText(getApplication(),"Besinleri roomdan aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromNet(){
        foodDownload.value = true
        disposable.add(
            foodApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        saveSqlite(t)
                        Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        foodMistake.value = true
                        foodDownload.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showFood(foodsList : List<Food>){
        foods.value = foodsList
        foodMistake.value = false
        foodDownload.value = false
    }

    private fun saveSqlite(foodsList: List<Food>){

        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodsList.toTypedArray())
            var i = 0
            while (i < foodsList.size){
                foodsList[i].uuid = uuidList[i].toInt()
                i += 1
            }
            showFood(foodsList)
        }
        MySharedPrefences.saveTime(System.nanoTime())
    }
}