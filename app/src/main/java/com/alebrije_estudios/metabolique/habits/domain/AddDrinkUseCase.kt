package com.alebrije_estudios.metabolique.habits.domain

import com.alebrije_estudios.metabolique.habits.data.HabitsRepository
import com.alebrije_estudios.metabolique.habits.ui.DrinksModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import javax.inject.Inject

class AddDrinkUseCase @Inject constructor(private val repository:HabitsRepository) {
    suspend operator fun invoke(authData: AuthData,localDate: LocalDate, drinkModel:DrinksModel):String{
        return repository.addDrink(authData.token, drinkModel.toEntity(authData.accountID,localDate.getYearMountDay()))
    }
}