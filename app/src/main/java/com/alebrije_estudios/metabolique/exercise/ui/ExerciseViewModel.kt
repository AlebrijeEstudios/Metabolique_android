package com.alebrije_estudios.metabolique.exercise.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alebrije_estudios.metabolique.exercise.domain.AddExerciseUseCase
import com.alebrije_estudios.metabolique.exercise.domain.DeleteExerciseUseCase
import com.alebrije_estudios.metabolique.exercise.domain.ListExercisesUseCase
import com.alebrije_estudios.metabolique.exercise.domain.UpdateExerciseUseCase
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val listExercisesUseCase: ListExercisesUseCase,
    private val addExerciseUseCase: AddExerciseUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
) : ViewModel() {
    val listTypes: List<String> by lazy {
        listOf(
            "Aerobico",
            "Cardio",
            "Anaeróbico",
            "Flexibilidad",
            "Estabilidad"
        )
    }
    val listIntensities: List<String> by lazy {
        listOf(
            "Leve",
            "Moderada",
            "Alta"
        )
    }
    val listTimes: List<String> by lazy {
        val list: MutableList<String> = mutableListOf()
        var time = 0
        repeat(10) {
            time += 30
            list.add("$time min")
        }
        list
    }
    private val _localDate: MutableLiveData<LocalDate> by lazy {
        val aux = MutableLiveData<LocalDate>()
        aux.value = LocalDate.now()
        aux
    }
    val localDate: LiveData<LocalDate> = _localDate
    private val _showDialogAddActivity: MutableLiveData<Boolean> = MutableLiveData()
    val showDialogAddActivity: LiveData<Boolean> = _showDialogAddActivity
    private val _listExercises = mutableStateListOf<ExercisesModel?>()
    var listExercises: List<ExercisesModel?> = _listExercises
    private val _showDialogAction: MutableLiveData<Boolean> = MutableLiveData()
    val showDialogAction: LiveData<Boolean> = _showDialogAction
    private val _exercise: MutableLiveData<ExercisesModel> = MutableLiveData()
    val exercise: LiveData<ExercisesModel> = _exercise
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        val aux = MutableLiveData<Boolean>()
        aux.value = false
        aux
    }
    val isLoading: LiveData<Boolean> = _isLoading


    fun showDialog() {
        _showDialogAddActivity.value = true
    }

    fun hiddenDialog() {
        _showDialogAddActivity.value = false
    }

    fun savesChanges(authData: AuthData, exercisesModel: ExercisesModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                if (exercisesModel.exerciseID.isNotBlank()) {
                    updateExerciseUseCase(authData, _localDate.value!!, exercisesModel)
                    //_listExercises.remove(exercisesModel)
                    //_listExercises.add(exercisesModel)
                } else {
                    val exercise = addExerciseUseCase(authData, _localDate.value!!, exercisesModel)
                    //_listExercises.clear()
                    _listExercises[_listExercises.lastIndex] = exercise
                    _listExercises.add(null)
                }
                listExercises = _listExercises
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                _isLoading.value = false
            }
        }
        _exercise.value = ExercisesModel("", "", "", -1)
        _showDialogAddActivity.value = false
    }

    fun changeDate(date: LocalDate, authData: AuthData) {
        _localDate.value = date
        _listExercises.clear()
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _listExercises.clear()
                _listExercises.addAll(listExercisesUseCase(authData, _localDate.value!!))
                _listExercises.add(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun getListOfExercises(authData: AuthData) {
        if (_listExercises.size == 0 && _isLoading.value == false) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    _listExercises.addAll(listExercisesUseCase(authData, _localDate.value!!))
                    _listExercises.add(null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun showDialogAction(exercisesModel: ExercisesModel) {
        _exercise.value = exercisesModel
        _showDialogAction.value = true
    }

    fun hideDialogAction() {
        _showDialogAction.value = false
    }

    fun editExercise(exercisesModel: ExercisesModel) {
        _exercise.value = exercisesModel
        _showDialogAddActivity.value = true
    }

    fun deleteExercise(authData: AuthData, exercisesModel: ExercisesModel) {
        viewModelScope.launch {
            _listExercises.remove(exercisesModel)
            deleteExerciseUseCase(authData.token, exercisesModel.exerciseID)
            listExercises = _listExercises
        }
    }

    fun onChange(exercise: ExercisesModel) {
        _exercise.value = exercise
    }
}