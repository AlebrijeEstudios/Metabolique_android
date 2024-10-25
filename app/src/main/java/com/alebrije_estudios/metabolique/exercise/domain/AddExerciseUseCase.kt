package com.alebrije_estudios.metabolique.exercise.domain

import com.alebrije_estudios.metabolique.exercise.data.ExerciseRepository
import com.alebrije_estudios.metabolique.exercise.ui.ExercisesModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import java.time.LocalDate
import javax.inject.Inject

class AddExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    suspend operator fun invoke(authData: AuthData,date: LocalDate, exercisesModel: ExercisesModel):ExercisesModel{
        val data = exerciseRepository.addExercise(
            token = authData.token,
            exerciseEntity = exercisesModel.toEntity(accountID = authData.accountID, date= date)
            )
        exercisesModel.exerciseID = data
        return exercisesModel
    }
}