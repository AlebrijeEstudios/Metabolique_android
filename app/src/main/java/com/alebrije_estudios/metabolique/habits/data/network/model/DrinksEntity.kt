package com.alebrije_estudios.metabolique.habits.data.network.model

import com.alebrije_estudios.metabolique.habits.ui.DrinksModel
import com.google.gson.annotations.SerializedName

data class DrinksEntity (
    @SerializedName("accountID") val accountID:String,
    @SerializedName("drinkDateHabit") val date:String,
    @SerializedName("drinkHabitID") val drinkHabitID:String,
    @SerializedName("typeDrink") val typeDrink:String,
    @SerializedName("amountConsumed") val amountConsumed:String
) {
    fun toModel():DrinksModel {
        return DrinksModel(
            drinkHabitID,
            typeDrink,
            amountConsumed.toInt()
        )
    }
}

