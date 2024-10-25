package com.alebrije_estudios.metabolique.exercise.domain

import com.alebrije_estudios.metabolique.exercise.data.ExerciseRepository
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    suspend operator fun invoke(token:String,exerciseID:String){
        exerciseRepository.deleteExercise(token,exerciseID)
    }
}