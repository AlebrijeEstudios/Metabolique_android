package com.alebrije_estudios.metabolique.medication.data.network

import android.util.Log
import com.alebrije_estudios.metabolique.medication.data.network.model.GraphicData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPUTData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPostData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicationService @Inject constructor(private val medicationClient: MedicationClient)  {
    suspend fun getMedications(token: String, accountID:String, date:String):Map<String, Any> {
        return withContext(Dispatchers.IO) {
            val response = medicationClient.getMedications(token, accountID, date)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error al obtener medicamentos: ${response.message()}")
                Log.i("Metabolique", response.errorBody()?.string() ?: "")
                throw Exception(response.message())
            }
            val listMedication: List<MedicationsData> = response.body()?.medicationList ?: listOf()
            val listWeeklyAttachments: List<GraphicData> =
                response.body()?.weeklyAttachments ?: listOf()
            mapOf("Medications" to listMedication.map { it.toModel() }, "Graphics" to listWeeklyAttachments)
        }
    }
    suspend fun addMedication(token: String, medicationData: MedicationsPostData): Map<String, Any> {
        return withContext(Dispatchers.IO) {
            val response = medicationClient.addMedication(token, medicationData)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error al crear medicamento: ${response.message()}")
                Log.i("Metabolique", response.errorBody()?.string()?: "")
                throw Exception(response.message())
            }
            val medication = response.body()?.medicationList?: MedicationsData(
                accountID = "",
                medicationID = "",
                medicationName = "",
                dosage = "",
                //frequency = "",
                startDate = "",
                endDate = "",
                times =listOf()
                )
            val weeklyAttachments = response.body()?.weeklyAttachments?: listOf()
            mapOf("Medication" to medication, "Graphics" to weeklyAttachments)
        }
    }
    suspend fun updateMedication(token: String, medicationData: MedicationsPUTData): Boolean {
        return withContext(Dispatchers.IO) {
            val response = medicationClient.editMedication(token, medicationData)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error al actualizar medicamento: ${response.message()}")
                Log.i("Metabolique", response.errorBody()?.string()?: "")
                throw Exception(response.message())
            }
            response.isSuccessful
        }
    }
    suspend fun deleteMedication(token: String, medicationID: String):Boolean{
        return withContext(Dispatchers.IO) {
            val response = medicationClient.deleteMedication(token, medicationID)
            if (!response.isSuccessful) {
                Log.e("Metabolique", "Error al eliminar medicamento: ${response.message()}")
                Log.i("Metabolique", response.errorBody()?.string()?: "")
                throw Exception(response.message())
            }
            response.isSuccessful
        }
    }
}