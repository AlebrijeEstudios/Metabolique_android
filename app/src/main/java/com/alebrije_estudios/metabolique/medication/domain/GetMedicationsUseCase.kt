package com.alebrije_estudios.metabolique.medication.domain

import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.medication.data.MedicationRepository
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import javax.inject.Inject

class GetMedicationsUseCase @Inject constructor(private val repository: MedicationRepository) {
    suspend operator fun invoke(authData: AuthData, date:LocalDate) = repository.getMedication(authData.token,authData.accountID, date.getYearMountDay())
}