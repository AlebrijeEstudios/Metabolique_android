package com.alebrije_estudios.metabolique.habits.data.network.model

import com.google.gson.annotations.SerializedName

data class SleepEntity(
    @SerializedName("accountID") val accountID:String,
    @SerializedName("dateRegister") val dateRegister:String,
    @SerializedName("sleepHabitID") val sleepID:String,
    @SerializedName("sleepHours") val sleepHours:Int,
    @SerializedName("perceptionOfRelaxation") val perceptionOfRelaxation:String
)
