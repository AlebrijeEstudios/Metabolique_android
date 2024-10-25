package com.alebrije_estudios.metabolique.exercise.data.network.model

import com.google.gson.annotations.SerializedName

data class ActiveMinutes(
    @SerializedName("timeSpentID") val timeSpentID: String,
    @SerializedName("timeSpent") val timeSpent: Int,
    @SerializedName("dateExercise") val date: String,
    @SerializedName("totalTimeSpent") val accountID: String,
)
