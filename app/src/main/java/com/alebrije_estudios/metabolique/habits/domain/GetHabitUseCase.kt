package com.alebrije_estudios.metabolique.habits.domain

import android.util.Log
import com.alebrije_estudios.metabolique.habits.data.HabitsRepository
import com.alebrije_estudios.metabolique.habits.data.network.HabitsServices
import com.alebrije_estudios.metabolique.habits.ui.HabitsModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import javax.inject.Inject

class GetHabitUseCase @Inject constructor(private val repository:HabitsRepository) {
    suspend operator fun invoke(authData:AuthData, date:LocalDate):HabitsModel{
        val data = repository.getHabits(authData.token,authData.accountID,date.getYearMountDay())
        Log.i("Metabolique", "data =$data")
        return data.toModel()
    }
}