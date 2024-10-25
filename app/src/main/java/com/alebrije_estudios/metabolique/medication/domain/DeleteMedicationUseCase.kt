package com.alebrije_estudios.metabolique.medication.domain

import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.medication.data.MedicationRepository
import javax.inject.Inject

class DeleteMedicationUseCase @Inject constructor(private val repository: MedicationRepository) {
    suspend operator fun invoke(authData:AuthData, medicationID:String){
        repository.deleteMedication(authData.token, medicationID)
    }
}