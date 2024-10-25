package com.alebrije_estudios.metabolique.habits.data.network

import android.util.Log
import com.alebrije_estudios.metabolique.habits.data.network.model.CigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.DrinksEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.HabitsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepCigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepEntity
import com.alebrije_estudios.metabolique.login.data.network.LoginClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HabitsServices @Inject constructor(private val habitsClient: HabitsClient) {
    suspend fun getHabits(token: String, accountID: String, date: String): HabitsEntity {
        return withContext(Dispatchers.IO) {
            val response = habitsClient.getHabits(token, accountID, date)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Getting habits error: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Obtener habitos")
            }
            HabitsEntity(
                response.body()?.drinksEntity ?: listOf(),
                response.body()?.sleepEntity ?: SleepEntity("", "", "", -1, ""),
                response.body()?.cigarsEntity ?: CigarsEntity("", "", "", -1, "")
            )

        }
    }

    suspend fun addDrink(token: String, drinksEntity: DrinksEntity): String {
        return withContext(Dispatchers.IO) {
            val response = habitsClient.addDrink(token, drinksEntity)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error adding drink: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Crear Bebidas")
            }
            response.body()?.drink?.drinkHabitID ?: ""
        }
    }

    suspend fun updateDrink(token: String, drinksEntity: DrinksEntity): Boolean {
        return withContext(Dispatchers.IO) {
            val response = habitsClient.editDrink(token, drinksEntity)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error updating drink: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Actualizar Bebidas")
            }
            response.isSuccessful
        }
    }

    suspend fun deleteDrink(token: String, drinkID: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = habitsClient.deleteDrink(token, drinkID)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error deleting drink: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Eliminar Bebidas")
            }
            response.isSuccessful
        }
    }

    /*suspend fun addCigar(token: String, cigarsEntity: CigarsEntity):String{
        return withContext(Dispatchers.IO) {
            val response = habitsClient.addDrug(token, cigarsEntity)
            if(!response.isSuccessful){
                Log.e("Metabolique", "Error adding cigar: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Crear Cigarros")
            }
            response.body()?.cigar?.cigarID?:""
        }
    }

    suspend fun updateCigar(token:String, cigarsEntity: CigarsEntity):Boolean{
        return withContext(Dispatchers.IO) {
            val response = habitsClient.editDrug(token, cigarsEntity)
            if(!response.isSuccessful){
                Log.e("Metabolique", "Error updating cigar: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Actualizar Cigarros")
            }
            response.isSuccessful
        }
    }*/

    suspend fun addSleep(token: String, sleepCigarsEntity: SleepCigarsEntity):Map<String,String> {
        return withContext(Dispatchers.IO) {
            val response = habitsClient.addSleep(token, sleepCigarsEntity)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error adding sleep and cigar: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Crear Sueño")
            }
            val sleepID = response.body()?.sleepID?:""
            val cigarID = response.body()?.cigarID?:""
            mapOf("sleepID" to sleepID, "cigarID" to cigarID)
        }
    }

    suspend fun updateSleep(token: String, sleepCigarsEntity: SleepCigarsEntity): Boolean {
        return withContext(Dispatchers.IO) {
            val response = habitsClient.editSleep(token, sleepCigarsEntity)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error updating sleep and cigar: ${response.errorBody()}")
                Log.e("Metabolique", response.message())
                throw Exception("Error al Actualizar Sueño")
            }
            response.isSuccessful
        }
    }

}