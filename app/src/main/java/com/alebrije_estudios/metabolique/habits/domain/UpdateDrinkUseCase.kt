package com.alebrije_estudios.metabolique.habits.domain

import com.alebrije_estudios.metabolique.habits.data.HabitsRepository
import com.alebrije_estudios.metabolique.habits.data.network.model.DrinksEntity
import com.alebrije_estudios.metabolique.habits.ui.DrinksModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import javax.inject.Inject

class UpdateDrinkUseCase @Inject constructor(private val repository: HabitsRepository) {
    suspend operator fun invoke(authData: AuthData,localDate: LocalDate, drink: DrinksModel){
        repository.updateDrink(authData.token, drink.toEntity(authData.accountID,localDate.getYearMountDay()))
    }
}