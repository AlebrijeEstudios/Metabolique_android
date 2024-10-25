package com.alebrije_estudios.metabolique.habits.data.network

import com.alebrije_estudios.metabolique.core.di.API_KEY
import com.alebrije_estudios.metabolique.habits.data.network.model.CigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.DrinksEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepCigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepEntity
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

interface HabitsClient {
    @Headers("Metabolique_API_KEY: $API_KEY")
    @GET("habits")
    suspend fun getHabits(@Header("Authorization") token:String, @Query("accountID") accountID:String, @Query("date") date:String): Response<HabitResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @POST("habits-drink")
    suspend fun addDrink(@Header("Authorization") token:String, @Body drink:DrinksEntity): Response<DrinkResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @PUT("habits-drink")
    suspend fun editDrink(@Header("Authorization") token:String, @Body drink:DrinksEntity): Response<DrinkResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @DELETE("habits-drink/{id}")
    suspend fun deleteDrink(@Header("Authorization") token:String, @Path("id") id:String): Response<DrinkResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @POST("habits-sleep-drugs")
    suspend fun addSleep(@Header("Authorization") token:String, @Body sleepCigarsEntity: SleepCigarsEntity): Response<SleepCigarResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @PUT("habits-sleep-drugs")
    suspend fun editSleep(@Header("Authorization") token:String, @Body sleepCigarsEntity: SleepCigarsEntity): Response<SleepCigarResponse>

    /*@Headers("Metabolique_API_KEY: $API_KEY")
    @DELETE("habits-sleep/{id}")
    fun deleteSleep(@Path("id") id:String): Response<SleepResponse>*/

    /*@Headers("Metabolique_API_KEY: $API_KEY")
    @POST("habits-drugs")
    suspend fun addDrug(@Header("Authorization") token:String, @Body drug: CigarsEntity): Response<CigarResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @PUT("habits-drugs")
    suspend fun editDrug(@Header("Authorization") token:String, @Body drug: CigarsEntity): Response<CigarResponse>*/

    /*@Headers("Metabolique_API_KEY: $API_KEY")
    @DELETE("habits-drugs/{id}")
    fun deleteDrug(@Path("id") id:String): Response<CigarResponse>*/
}