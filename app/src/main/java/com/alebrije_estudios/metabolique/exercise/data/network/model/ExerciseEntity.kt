package com.alebrije_estudios.metabolique.exercise.data.network.model

import com.alebrije_estudios.metabolique.exercise.ui.ExercisesModel
import com.alebrije_estudios.metabolique.login.data.network.model.Date
import com.google.gson.annotations.SerializedName

data class ExerciseEntity(
    @SerializedName("exerciseID") val exerciseID: String,
    @SerializedName("accountID") val accountID: String,
    @SerializedName("dateExercise") val dateExercise: String,
    @SerializedName("typeExercise") val typeExercise: String,
    @SerializedName("intensityExercise") val intensityExercise: String,
    @SerializedName("timeSpent") val timeSpent: Int
){
    fun toModel(): ExercisesModel = ExercisesModel(
        exerciseID = exerciseID,
        type = typeExercise,
        intensity = intensityExercise,
        time = timeSpent
    )
}
