package com.alebrije_estudios.metabolique.habits.data.network.model

import com.alebrije_estudios.metabolique.habits.ui.HabitsModel
import com.google.gson.annotations.SerializedName


data class HabitsEntity(
    @SerializedName("drinkConsumed") val drinkConsumed:List<DrinksEntity>,
    @SerializedName("hoursSleepConsumed") val hoursSleep:SleepEntity,
    @SerializedName("drugsConsumed") val cigars:CigarsEntity
) {
    fun toModel(): HabitsModel {
    return HabitsModel(
        sleepID = hoursSleep.sleepID,
        cigarID = cigars.cigarID,
        drinkConsumed = drinkConsumed.map { it.toModel() },
        cigarsConsumed = cigars.cigarsConsumed,
        emotionState = cigars.emotionState,
        sleepHours = hoursSleep.sleepHours,
        perceptionOfRelaxation = hoursSleep.perceptionOfRelaxation
    )
    }
}
