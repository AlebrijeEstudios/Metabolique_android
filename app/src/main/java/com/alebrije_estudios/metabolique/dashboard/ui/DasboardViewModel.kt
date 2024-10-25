package com.alebrije_estudios.metabolique.dashboard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class DashboardViewModel @Inject constructor():ViewModel() {
    fun getData():DashboardModel{
        return DashboardModel(
            calories = 500,
            countMedication = 2,
            exerciseTime = 15,
            sleepTime = 4*60
        )
    }
}

fun DashboardModel.getExerciseTime():String ="${(exerciseTime/60).toString().padStart(2, '0')}:${(exerciseTime%60).toString().padStart(2, '0')}"
fun DashboardModel.getSleepTime():String ="${(sleepTime/60).toString().padStart(2, '0')}:${(sleepTime%60).toString().padStart(2,'0')}h"
fun DashboardModel.getCountMedication():String ="$countMedication/$maxCountMedication"