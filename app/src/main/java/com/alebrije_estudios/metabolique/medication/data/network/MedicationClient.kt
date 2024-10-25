package com.alebrije_estudios.metabolique.medication.data.network

import com.alebrije_estudios.metabolique.core.di.API_KEY
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPUTData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPostData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MedicationClient {
    @GET("medication")
    @Headers("Metabolique_API_KEY: $API_KEY" )
    suspend fun getMedications(@Header("Authorization") token: String, @Query("accountID") accountID:String, @Query("date") date:String): Response<MedicationGetResponse>
    @POST("medication")
    @Headers("Metabolique_API_KEY: $API_KEY")
    suspend fun addMedication(@Header("Authorization") token: String, @Body medication: MedicationsPostData): Response<MedicationPostResponse>
    @PUT("medication")
    @Headers("Metabolique_API_KEY: $API_KEY")
    suspend fun editMedication(@Header("Authorization") token: String, @Body medication: MedicationsPUTData): Response<MedicationPostResponse>
    @DELETE("medication")
    @Headers("Metabolique_API_KEY: $API_KEY")
    suspend fun deleteMedication(@Header("Authorization") token: String, id:String): Response<MedicationDeleteResponse>
}