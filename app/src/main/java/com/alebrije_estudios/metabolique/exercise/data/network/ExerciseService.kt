package com.alebrije_estudios.metabolique.exercise.data.network

import android.util.Log
import com.alebrije_estudios.metabolique.exercise.data.network.model.ExerciseEntity
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.data.network.model.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExerciseService @Inject constructor(private val exerciseClient: ExerciseClient) {
    suspend fun getExercises(token:String, id:String,date: String):List<ExerciseEntity> {
        return withContext(Dispatchers.IO){
            Log.i("Metabolique Exercises", "id = $id, date = $date, token = $token")
            val response = exerciseClient.getExercises(token = token,id=id, date =date)

            if(!response.isSuccessful){
                Log.e("Metabolique Exercises", "Error al obtener ejercicios: ${response.message()}")
                Log.i("Metabolique Exercises", response.errorBody()?.string()?:"")
                throw Exception(response.message())
            }
            response.body()?.exercise?: listOf()
//            listOf()
        }
    }
    suspend fun addExercise(token: String, exerciseEntity: ExerciseEntity):String {
        return withContext(Dispatchers.IO) {
            val response = exerciseClient.addExercise(token = token, exercise = exerciseEntity)
            if(!response.isSuccessful)throw Exception(response.message())
            Log.i("Metabolique Exercises", "body: ${response.body()}")
            response.body()?.exercise?.exerciseID?:""
        }
    }
    suspend fun updateExercise(token: String, exerciseEntity: ExerciseEntity):Boolean {
        return withContext(Dispatchers.IO) {
            val response = exerciseClient.updateExercise(token = token, exercise = exerciseEntity)
            if(!response.isSuccessful)throw Exception(response.message())
            response.isSuccessful
        }
    }
    suspend fun deleteExercise(token: String, exerciseID: String):Boolean {
        return withContext(Dispatchers.IO) {
            val response = exerciseClient.deleteExercise(token = token, id = exerciseID)
            if(!response.isSuccessful)throw Exception(response.message())
            response.isSuccessful
        }
    }
}