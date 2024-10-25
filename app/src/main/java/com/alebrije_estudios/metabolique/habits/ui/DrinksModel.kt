package com.alebrije_estudios.metabolique.habits.ui

import com.alebrije_estudios.metabolique.habits.data.network.model.DrinksEntity

data class DrinksModel(var drinkID:String = "", var name: String, var count: Int) {
    fun toEntity(accountID:String, date:String): DrinksEntity = DrinksEntity(
        accountID = accountID,
        date = date,
        drinkHabitID = drinkID,
        typeDrink = name,
        amountConsumed = count.toString()
    )
}
