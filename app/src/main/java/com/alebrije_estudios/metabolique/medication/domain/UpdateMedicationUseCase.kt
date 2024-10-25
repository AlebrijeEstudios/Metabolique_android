package com.alebrije_estudios.metabolique.medication.domain

import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.medication.data.MedicationRepository
import com.alebrije_estudios.metabolique.medication.ui.MedicationModel
import java.time.LocalDate
import javax.inject.Inject

class UpdateMedicationUseCase @Inject constructor(private val repository: MedicationRepository) {
    suspend operator fun invoke(authData: AuthData,localDate: LocalDate, medication: MedicationModel) = repository.updateMedication(authData.token, medication.toEntityPUT(authData,localDate))
}