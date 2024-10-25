package com.alebrije_estudios.metabolique.habits.data.network

import com.alebrije_estudios.metabolique.habits.data.network.model.CigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.DrinksEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.HabitsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepEntity
import com.google.gson.annotations.SerializedName

data class HabitResponse(
    @SerializedName("message") val message: String,
    //@SerializedName("Habits") val habits: HabitsEntity,
    @SerializedName("drugsConsumed") val cigarsEntity:CigarsEntity,
    @SerializedName("hoursSleepConsumed") val sleepEntity: SleepEntity,
    @SerializedName("drinkConsumed") val drinksEntity: List<DrinksEntity>
)

data class DrinkResponse(@SerializedName("message") val message: String, @SerializedName("drinksConsumed") val drink: DrinksEntity)

//data class CigarResponse(@SerializedName("message") val message: String, @SerializedName("drugsConsumed") val cigar:CigarsEntity)

data class SleepCigarResponse(
    @SerializedName("message") val message: String,
    @SerializedName("sleepHabitID") val sleepID:String,
    @SerializedName("drugsHabitID") val cigarID:String
)