package com.alebrije_estudios.metabolique.exercise.ui

import com.alebrije_estudios.metabolique.exercise.data.network.model.ExerciseEntity
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import java.time.LocalTime

data class ExercisesModel(
    var exerciseID: String,
    var type: String,
    var intensity: String,
    var time: Int,
    //var activeMinutesModel: List<ActiveMinutesModel> = listOf(),
    var totalTimeSpent:String = ""
) {
    fun toEntity(accountID: String, date: LocalDate) = ExerciseEntity(
        exerciseID = exerciseID,
        dateExercise = date.getYearMountDay(),
        accountID = accountID,
        typeExercise = type,
        intensityExercise = intensity,
        timeSpent = time
    )
}