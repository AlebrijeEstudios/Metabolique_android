package com.alebrije_estudios.metabolique.habits.data.network.model

import com.google.gson.annotations.SerializedName

data class CigarsEntity(
    @SerializedName("accountID") val accountID:String,
    @SerializedName("dateRegister") val dateRegister:String,
    @SerializedName("drugsHabitID") val cigarID:String,
    @SerializedName("cigarettesSmoked") val cigarsConsumed:Int,
    @SerializedName("predominantEmotionalState") val emotionState:String
)
