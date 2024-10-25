package com.alebrije_estudios.metabolique.exercise.domain

import com.alebrije_estudios.metabolique.exercise.data.ExerciseRepository
import com.alebrije_estudios.metabolique.exercise.ui.ExercisesModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import java.time.LocalDate
import javax.inject.Inject

class UpdateExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    suspend operator fun invoke(authData: AuthData,date: LocalDate, exercise: ExercisesModel) {
        exerciseRepository.updateExercise(
            token = authData.token,
            exerciseEntity = exercise.toEntity(authData.accountID,date)
        )
    }
}
