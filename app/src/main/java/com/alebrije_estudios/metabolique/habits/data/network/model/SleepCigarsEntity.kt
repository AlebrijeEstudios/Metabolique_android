package com.alebrije_estudios.metabolique.habits.data.network.model

import com.google.gson.annotations.SerializedName

data class SleepCigarsEntity(
    @SerializedName("accountID") val accountID:String,
    @SerializedName("dateRegister") val dateRegister:String,
    @SerializedName("sleepHabitID") val sleepID:String,
    @SerializedName("sleepHours") val sleepHours:Int,
    @SerializedName("perceptionOfRelaxation") val perceptionOfRelaxation:String,
    @SerializedName("drugsHabitID") val cigarID:String,
    @SerializedName("cigarettesSmoked") val cigarsConsumed:Int,
    @SerializedName("predominantEmotionalState") val emotionState:String
)
