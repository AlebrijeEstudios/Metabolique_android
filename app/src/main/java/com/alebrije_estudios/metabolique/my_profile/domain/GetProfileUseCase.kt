package com.alebrije_estudios.metabolique.my_profile.domain

import com.alebrije_estudios.metabolique.login.data.LoginRepository
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val loginRepository: LoginRepository){
    suspend operator fun invoke(authData: AuthData) = loginRepository.getProfile(authData =authData )
}