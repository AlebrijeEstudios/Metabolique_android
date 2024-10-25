package com.alebrije_estudios.metabolique.food_capture.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
@HiltViewModel
class FoodCaptureViewModel @Inject constructor(): ViewModel() {
    var date: LocalDate = LocalDate.of(2024, 7 , 1)
    var time: LocalTime = LocalTime.of(13, 30)
}