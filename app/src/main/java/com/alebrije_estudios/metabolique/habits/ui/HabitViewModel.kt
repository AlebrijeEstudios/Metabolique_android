package com.alebrije_estudios.metabolique.habits.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alebrije_estudios.metabolique.habits.domain.AddDrinkUseCase
import com.alebrije_estudios.metabolique.habits.domain.DeleteDrinkUseCase
import com.alebrije_estudios.metabolique.habits.domain.GetHabitUseCase
import com.alebrije_estudios.metabolique.habits.domain.UpdateDrinkUseCase
import com.alebrije_estudios.metabolique.habits.domain.UpdateHabitUseCase
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitUseCase,
    private val addDrinkUseCase: AddDrinkUseCase,
    private val updateDrinkUseCase: UpdateDrinkUseCase,
    private val deleteDrinkUseCase: DeleteDrinkUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
) : ViewModel() {
    private var _cigarsID: String = ""
    private val _cigarsConsumed: MutableLiveData<String> = MutableLiveData()
    val cigarsConsumed: LiveData<String> = _cigarsConsumed
    private val _emotionalState = mutableStateListOf<String>()
    val emotionalState: List<String> = _emotionalState
    private val _localDate: MutableLiveData<LocalDate> by lazy {
        val aux = MutableLiveData<LocalDate>()
        aux.value = LocalDate.now()
        aux
    }
    val localDate: LiveData<LocalDate> = _localDate
    private val _listDrinksConsumer = mutableStateListOf<DrinksModel?>()
    val listDrinksConsumer: List<DrinksModel?> = _listDrinksConsumer
    private var _sleepID: String = ""
    private val _hoursSleep: MutableLiveData<String> = MutableLiveData()
    val hoursSleep: LiveData<String> = _hoursSleep
    private val _perceptionRest: MutableLiveData<String> = MutableLiveData()
    val perceptionRest: LiveData<String> = _perceptionRest
    private val _showDialogAddDrink: MutableLiveData<Boolean> = MutableLiveData()
    val showDialogAddDrink: LiveData<Boolean> = _showDialogAddDrink
    var _drink:MutableLiveData<DrinksModel> = MutableLiveData()
    val drink:LiveData<DrinksModel> = _drink
    val listDrinks: List<String> by lazy {
        listOf("Refresco", "Cerveza", "Vino", "Vodka", "Cafe")
    }
    val listConsumer: List<String> by lazy {
        val aux = mutableListOf<String>()
        repeat(11) {
            aux.add("$it al dia")
        }
        aux
    }
    val listEmotionalState: List<String> by lazy {
        listOf(
            "Normal",
            "Triste",
            "Enojado",
            "Feliz"
        )
    }
    val listCups: List<String> by lazy {
        val aux = mutableListOf<String>()
        repeat(20){
            aux.add("$it copas")
        }
        aux
    }
    val listPerceptionRest: List<String> by lazy {
        listOf(
            "Normal",
            "Desconcentrado",
            "Enojado",
            "Triste",
        )
    }
    val listHoursSleep: List<String> by lazy {
        val list: MutableList<String> = mutableListOf()
        repeat(13) {
            list.add("$it horas")
        }
        list
    }
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        val aux = MutableLiveData<Boolean>()
        aux.value = false
        aux
    }
    val isLoading: LiveData<Boolean> = _isLoading
    /*fun getListDrinks(authData: AuthData){
        if(_listDrinksConsumer.size ==0){ //_listDrinks.add(DrinksModel(name = "Vino", count = 1))
            viewModelScope.launch {
                try {
                    getHabitsUseCase(authData, _localDate.value!!)
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }*/

    fun getHabits(authData: AuthData) {
        if(_listDrinksConsumer.size ==0 && _isLoading.value == false) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    val data = getHabitsUseCase(authData, _localDate.value!!)
                    _hoursSleep.value = if(data.sleepHours > -1) data.sleepHours.toString()+" horas" else ""
                    _cigarsConsumed.value = if(data.cigarsConsumed > -1) data.cigarsConsumed.toString()+" al dia" else ""
                    _emotionalState.clear()
                    _emotionalState.addAll(data.emotionState.split(", "))
                    _perceptionRest.value = data.perceptionOfRelaxation
                    _listDrinksConsumer.addAll(data.drinkConsumed)
                    _listDrinksConsumer.add(null)
                    _sleepID = data.sleepID
                    _cigarsID = data.cigarID
                    Log.i("Metabolique", "sleepID= ${data.sleepID}  cigarID=$_cigarsID")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun addDrink(authData: AuthData, drink: DrinksModel) {
        viewModelScope.launch {
            try {
                if (drink.drinkID.isNotBlank()) {
                    updateDrinkUseCase(authData,_localDate.value!!, drink)
                } else{
                    drink.drinkID = addDrinkUseCase(authData,_localDate.value!!, drink)
                    _listDrinksConsumer[_listDrinksConsumer.lastIndex] = drink
                    _listDrinksConsumer.add(null)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onChangeHabits(
        authData: AuthData,
        cigarsConsumed: String,
        emotionState: List<String>,
        perceptionOfRelaxation: String,
        sleepHours: String,
    ) {
        if (sleepHours.isBlank() || cigarsConsumed.isBlank() || perceptionOfRelaxation.isBlank() || emotionState.isEmpty()) {
            _cigarsConsumed.value = cigarsConsumed
            _hoursSleep.value = sleepHours
            _emotionalState.clear()
            _emotionalState.addAll( emotionState)
            _perceptionRest.value = perceptionOfRelaxation
            return
        }
        viewModelScope.launch {
            try {
                _cigarsConsumed.value = cigarsConsumed
                _hoursSleep.value = sleepHours
                _emotionalState.clear()
                _emotionalState.addAll( emotionState)
                var auxEmotionalState = ""
                emotionState.forEach {
                    auxEmotionalState += "$it, "
                }
                auxEmotionalState = auxEmotionalState.dropLast(2)
                _perceptionRest.value = perceptionOfRelaxation
                val data = HabitsModel(
                    cigarID = _cigarsID,
                    cigarsConsumed = _cigarsConsumed.value?.replace(" al dia","")?.toIntOrNull() ?: -1,
                    drinkConsumed = listOf(),
                    emotionState = auxEmotionalState,
                    perceptionOfRelaxation = perceptionOfRelaxation,
                    sleepHours = _hoursSleep.value?.replace(" horas","")?.toIntOrNull() ?: -1,
                    sleepID = _sleepID
                )
                Log.i("Metabolique", "sleepID= $_sleepID  cigarID=$_cigarsID")
               val newData = updateHabitUseCase(authData,_localDate.value!!, data)
                newData["sleepID"]?.let {
                    _sleepID = it
                }
                newData["cigarID"]?.let{
                    _cigarsID = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun showDialogAddDrink() {
        _drink.value = DrinksModel("","", -1)
        _showDialogAddDrink.value = true
    }

    fun hiddenDialogAddDrink() {
        _showDialogAddDrink.value = false
    }

    fun onDateChanged(authData: AuthData, localDate: LocalDate) {
        _localDate.value = localDate
        viewModelScope.launch {
            try {
                _listDrinksConsumer.clear()
                val data = getHabitsUseCase(authData, _localDate.value!!)
                _hoursSleep.value = if(data.sleepHours >-1) data.sleepHours.toString()+" horas" else ""
                _cigarsConsumed.value = if(data.cigarsConsumed > -1) data.cigarsConsumed.toString()+" dia" else ""
                _emotionalState.clear()
                _emotionalState.addAll(data.emotionState.split(", "))
                _perceptionRest.value = data.perceptionOfRelaxation
                _listDrinksConsumer.addAll(data.drinkConsumed)
                _listDrinksConsumer.add(null)
                _sleepID = data.sleepID
                _cigarsID = data.cigarID
                Log.i("Metabolique", "sleepID= $_sleepID  cigarID=$_cigarsID")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteDrink(authData: AuthData, drink: DrinksModel) {
        viewModelScope.launch {
            try {
                deleteDrinkUseCase(authData.token, drink.drinkID)
                _listDrinksConsumer.remove(drink)
                getHabitsUseCase(authData, _localDate.value!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editDrink(drink: DrinksModel) {
        _drink.value = drink
        _showDialogAddDrink.value = true
    }

}