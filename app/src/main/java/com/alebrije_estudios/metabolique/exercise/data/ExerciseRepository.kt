package com.alebrije_estudios.metabolique.exercise.data;

import com.alebrije_estudios.metabolique.exercise.data.network.ExerciseService
import com.alebrije_estudios.metabolique.exercise.data.network.model.ExerciseEntity
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.data.network.model.Date
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val api:ExerciseService){
    suspend fun getExercise(token:String, id:String,date: String) = api.getExercises(token,id,date)

    suspend fun addExercise(token: String, exerciseEntity: ExerciseEntity) = api.addExercise(token, exerciseEntity)

    suspend fun updateExercise(token: String, exerciseEntity: ExerciseEntity) = api.updateExercise(token, exerciseEntity)

    suspend fun deleteExercise(token: String, exerciseID: String) = api.deleteExercise(token, exerciseID)
}
