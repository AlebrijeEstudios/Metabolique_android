package com.alebrije_estudios.metabolique.exercise.data.network

import com.alebrije_estudios.metabolique.core.di.API_KEY
import com.alebrije_estudios.metabolique.exercise.data.network.model.ExerciseEntity
import com.alebrije_estudios.metabolique.exercise.data.network.model.ExerciseGetResponse
import com.alebrije_estudios.metabolique.exercise.data.network.model.ExercisePostResponse
import com.alebrije_estudios.metabolique.exercise.data.network.model.ExercisePutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseClient {
    @Headers("Metabolique_API_KEY: $API_KEY")
    @GET("exercises")
    suspend fun getExercises(@Header("Authorization") token:String, @Query("accountID") id:String,@Query("date") date: String): Response<ExerciseGetResponse>
    @Headers("Metabolique_API_KEY: $API_KEY")
    @POST("exercises")
    suspend fun addExercise(@Header("Authorization") token: String ,@Body exercise: ExerciseEntity): Response<ExercisePostResponse>
    @Headers("Metabolique_API_KEY: $API_KEY")
    @PUT("exercises/")
    suspend fun updateExercise(@Header("Authorization") token: String, @Body exercise: ExerciseEntity): Response<ExercisePostResponse>
    @Headers("Metabolique_API_KEY: $API_KEY")
    @DELETE("exercises/{id}")
    suspend fun deleteExercise(@Header("Authorization") token: String, @Path("id") id:String): Response<ExercisePutResponse>
}