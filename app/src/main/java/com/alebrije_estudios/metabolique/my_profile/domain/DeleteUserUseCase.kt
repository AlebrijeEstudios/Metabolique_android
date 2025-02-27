package com.alebrije_estudios.metabolique.my_profile.domain

import com.alebrije_estudios.metabolique.login.data.LoginRepository
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: LoginRepository){
    suspend operator fun invoke( authData: AuthData) = repository.deleteAccount(authData = authData)
}