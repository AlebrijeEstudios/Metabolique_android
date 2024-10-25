package com.alebrije_estudios.metabolique.login.data

import com.alebrije_estudios.metabolique.login.data.network.LoginService
import javax.inject.Inject


class LoginRepository @Inject constructor(private val api: LoginService) {
    suspend fun login(email: String, password: String) = api.doLogin(email, password)
}