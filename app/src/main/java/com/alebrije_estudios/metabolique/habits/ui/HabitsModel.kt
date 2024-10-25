package com.alebrije_estudios.metabolique.habits.ui

data class HabitsModel(
    val cigarID:String = "",
    val drinkConsumed:List<DrinksModel>,
    val cigarsConsumed:Int,
    val emotionState: String,
    val sleepID:String = "",
    val sleepHours: Int,
    val perceptionOfRelaxation: String
)
