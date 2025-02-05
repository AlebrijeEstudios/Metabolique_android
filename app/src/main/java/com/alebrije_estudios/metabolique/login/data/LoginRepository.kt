package com.alebrije_estudios.metabolique.login.data

import com.alebrije_estudios.metabolique.login.data.network.LoginService
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import javax.inject.Inject


class LoginRepository @Inject constructor(private val api: LoginService) {
    suspend fun login(email: String, password: String) = api.doLogin(email, password)
    suspend fun getProfile(authData: AuthData) = api.getAccount(authData)
    suspend fun deleteAccount(authData: AuthData) = api.deleteAccount(authData)
}