package com.alebrije_estudios.metabolique.exercise.data.network.model

import com.google.gson.annotations.SerializedName

data class ExerciseGetResponse(
    @SerializedName("message") val message: String,
    @SerializedName("exercises") val exercise: List<ExerciseEntity>,
    @SerializedName("activeMinutes") val activeMinutes: List<ActiveMinutes>
)

data class ExercisePostResponse(
    @SerializedName("message") val message: String,
    @SerializedName("exercise")val exercise: ExerciseEntity
)

data class ExercisePutResponse(val message: String)