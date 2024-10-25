package com.alebrije_estudios.metabolique.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel(){
    //private val _listFoods:MutableList<Food> = getListFoods()
    //val listFoods: MutableList<Food> = getListFoods()
    private val _date:MutableLiveData<LocalDate> by lazy {
        val date = MutableLiveData<LocalDate>()
        date.value = LocalDate.now()
        date
    }
    val date:LiveData<LocalDate> = _date

    fun onDateChanged(date: LocalDate) {
        _date.value = date
    }
    fun getListFoods():MutableList<FoodModel?>{
        return mutableListOf(
            FoodModel(name = "Desayuno", calories = 400, isCheck = true),
            FoodModel(name = "Snack", calories = 100, isCheck = true),
            FoodModel(name = "Comida", calories = 0),
            FoodModel(name = "Snack", calories = 0),
            FoodModel(name = "Cena", calories = 0),
            FoodModel(name = "Comida extra 1", calories = 0),
            FoodModel(name = "Comida extra 2", calories = 0),
            FoodModel(name = "Comida extra 3", calories = 0),
            FoodModel(name = "Comida extra 4", calories = 0),
            null
            )
    }
}
