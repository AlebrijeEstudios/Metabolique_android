package com.alebrije_estudios.metabolique.exercise.domain

import com.alebrije_estudios.metabolique.exercise.data.ExerciseRepository
import com.alebrije_estudios.metabolique.exercise.ui.ExercisesModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import javax.inject.Inject

class ListExercisesUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    suspend operator fun invoke (authData: AuthData,date: LocalDate):List<ExercisesModel> {
        val newExercises:MutableList<ExercisesModel> = mutableListOf()
        val exercises = exerciseRepository.getExercise(authData.token, authData.accountID,date.getYearMountDay())
        exercises.forEach {
            newExercises.add(it.toModel())
        }
        return newExercises
    }
}