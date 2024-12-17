package com.alebrije_estudios.metabolique.food_capture.ui

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.libraries.places.api.model.kotlin.localTime
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
@HiltViewModel
class FoodCaptureViewModel @Inject constructor(): ViewModel() {
    private val listFoodsC = mutableStateListOf<FoodCModel>()
    private val listFoods = mutableStateListOf<String>()
    private val _srcImage = MutableLiveData<Uri>()
    private val _search = MutableLiveData<String>("")
    val search: LiveData<String> = _search
    val srcImage: LiveData<Uri> = _srcImage
    var time: LocalTime = LocalTime.now()
    var date: LocalDate = LocalDate.of(2024, 7 , 1)
    private val _showModalBottomSheet:MutableLiveData<Boolean> = MutableLiveData(false)
    val showModalBottomSheet:LiveData<Boolean> = _showModalBottomSheet

    fun getFoods(date: LocalDate):List<FoodCModel> {
        listFoodsC += FoodCModel("Manzana", "3/4", "Porcion", 1)
        listFoodsC += FoodCModel("Pera", "1/2", "Porcion", 2)
        listFoodsC += FoodCModel("Naranja", "1/3","Porcion", 3)
        listFoodsC += FoodCModel("Plátano", "1/4", "Porcion",4)
        return listFoodsC
    }

    fun addFood(food: FoodCModel) {
        listFoodsC += food
    }

    fun removeFood(food: FoodCModel) {
        listFoodsC.remove(food)
    }

    fun updateFood(food: FoodCModel, index: Int) {
        listFoodsC[index] = food
    }

    fun getFood(index: Int): FoodCModel {
        return listFoodsC[index]
    }

    fun getListFoods(): SnapshotStateList<String> {
        listFoods += "Manzana"
        listFoods += "Pera"
        listFoods += "Naranja"
        listFoods += "Plátano"
        return listFoods
    }

    fun showModalBottomSheet() {
        _showModalBottomSheet.value = true
    }
    fun hideModalBottomSheet() {
        _showModalBottomSheet.value = false
    }

    fun updateSrcImage(uri: Uri) {
        _srcImage.value = uri
    }

    fun updateSearch(search: String) {
        _search.value = search
    }
}
