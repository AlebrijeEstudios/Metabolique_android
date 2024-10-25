package com.alebrije_estudios.metabolique.habits.domain

import com.alebrije_estudios.metabolique.habits.data.HabitsRepository
import javax.inject.Inject

class DeleteDrinkUseCase @Inject constructor(private val repository: HabitsRepository) {
    suspend operator fun invoke(token:String, drinkID:String){
        repository.deleteDrink(token, drinkID)
    }
}