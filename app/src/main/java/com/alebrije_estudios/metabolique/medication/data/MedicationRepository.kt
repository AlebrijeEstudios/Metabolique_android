package com.alebrije_estudios.metabolique.medication.data

import com.alebrije_estudios.metabolique.medication.data.network.MedicationService
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPUTData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPostData
import javax.inject.Inject

class MedicationRepository @Inject constructor(private val api: MedicationService) {
    suspend fun getMedication(token:String, accountID:String,date:String) = api.getMedications(token, accountID,date)

    suspend fun addMedication(token:String, medication: MedicationsPostData) = api.addMedication(token, medication)

    suspend fun updateMedication(token:String, medication: MedicationsPUTData) = api.updateMedication(token, medication)

    suspend fun deleteMedication(token:String, medicationID: String) = api.deleteMedication(token, medicationID)
}