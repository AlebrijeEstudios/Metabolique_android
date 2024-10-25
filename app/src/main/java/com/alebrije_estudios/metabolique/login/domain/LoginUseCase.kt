package com.alebrije_estudios.metabolique.login.domain

import com.alebrije_estudios.metabolique.login.data.LoginRepository
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import dagger.Module
import javax.inject.Inject


class LoginUseCase @Inject  constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(user:String, password:String): AuthData? {
        return repository.login(user, password)
    }
}