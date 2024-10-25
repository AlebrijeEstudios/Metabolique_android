package com.alebrije_estudios.metabolique.my_profile.ui

import java.time.LocalDate

data class UserModel(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val birthDate: LocalDate,
    val gender: String,
    val status: Number,
    val weight: Number,
    val protocolToFollow: String)
