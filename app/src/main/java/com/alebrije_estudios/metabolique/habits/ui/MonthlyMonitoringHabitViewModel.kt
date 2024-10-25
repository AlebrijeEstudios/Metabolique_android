package com.alebrije_estudios.metabolique.habits.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alebrije_estudios.metabolique.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MonthlyMonitoringHabitViewModel @Inject constructor():ViewModel() {
    private val _answer1: MutableLiveData<LocalTime> = MutableLiveData()
    val answer1: LiveData<LocalTime> = _answer1
    private val _answer2: MutableLiveData<String> = MutableLiveData()
    val answer2: LiveData<String> = _answer2
    private val _answer3: MutableLiveData<LocalTime> = MutableLiveData()
    val answer3: LiveData<LocalTime> = _answer3
    private val _answer4: MutableLiveData<String> = MutableLiveData()
    val answer4: LiveData<String> = _answer4
    private val _answer5 = mutableStateListOf(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1)
    val answer5: List<Int> = _answer5
    private val _answer6: MutableLiveData<Int> = MutableLiveData()
    val answer6: LiveData<Int> = _answer6
    private val _answer7: MutableLiveData<Int> = MutableLiveData()
    val answer7: LiveData<Int> = _answer7
    private val _answer8: MutableLiveData<Int> = MutableLiveData()
    val answer8: LiveData<Int> = _answer8
    private val _answer9: MutableLiveData<Int> = MutableLiveData()
    val answer9: LiveData<Int> = _answer9
    @Composable
    fun optionFrequencyWeekly():List<String>{
        return listOf(
            stringResource(id = R.string.text_opcional_frequency_weekly1),
            stringResource(id = R.string.text_opcional_frequency_weekly2),
            stringResource(id = R.string.text_opcional_frequency_weekly3),
            stringResource(id = R.string.text_opcional_frequency_weekly4)
            )
    }
    @Composable
    fun optionsValuationOptions():List<String>{
        return listOf(
            stringResource(id = R.string.text_opcional_valuation1),
            stringResource(id = R.string.text_opcional_valuation2),
            stringResource(id = R.string.text_opcional_valuation3),
            stringResource(id = R.string.text_opcional_valuation4)
            )
    }
    @Composable
    fun optionsDifficulty():List<String>{
        return listOf(
            stringResource(id = R.string.text_opcional_difficulty1),
            stringResource(id = R.string.text_opcional_difficulty2),
            stringResource(id = R.string.text_opcional_difficulty3),
            stringResource(id = R.string.text_opcional_difficulty4)
            )
    }
    fun changedAnswers(
        answer1:LocalTime?,
        answer2:String,
        answer3:LocalTime?,
        answer4:String,
        answer6:Int,
        answer7:Int,
        answer8:Int,
        answer9:Int
    ){
        answer1.let { _answer1.value =it }
        _answer2.value = answer2
        answer3.let { _answer3.value = it }
        _answer4.value = answer4
        _answer6.value = answer6
        _answer7.value = answer7
        _answer8.value = answer8
        _answer9.value = answer9
    }
    fun changedAnswers5(answer5: Int, index:Int){
        _answer5[index] = answer5
    }
}