package com.alebrije_estudios.metabolique.habits.data

import com.alebrije_estudios.metabolique.habits.data.network.HabitsServices
import com.alebrije_estudios.metabolique.habits.data.network.model.DrinksEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepCigarsEntity
import javax.inject.Inject

class HabitsRepository @Inject constructor(private val api: HabitsServices) {
    suspend fun getHabits(token:String, accountID:String, date:String) = api.getHabits(token,accountID,date)
    suspend fun addDrink(token:String, drink:DrinksEntity) = api.addDrink(token, drink)
    suspend fun updateDrink(token:String, drink: DrinksEntity) = api.updateDrink(token, drink)
    suspend fun deleteDrink(token:String, drinkID: String) = api.deleteDrink(token, drinkID)
    suspend fun addSleep(token:String, sleepCigarsEntity:SleepCigarsEntity) = api.addSleep(token, sleepCigarsEntity)
    suspend fun updateSleep(token:String, sleepCigarsEntity:SleepCigarsEntity) = api.updateSleep(token, sleepCigarsEntity)
//    suspend fun deleteSleep(sleepID: String) = api.deleteSleep(sleepID)
    /*suspend fun addCigar(token:String, cigar: CigarsEntity) = api.addCigar(token, cigar)
    suspend fun updateCigar(token:String, cigar: CigarsEntity) = api.updateCigar(token, cigar)*/
//    suspend fun deleteCigar(cigarID: String) = api.deleteCigar(cigarID)
}