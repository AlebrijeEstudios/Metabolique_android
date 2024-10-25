package com.alebrije_estudios.metabolique.login.data.network.model

import retrofit2.http.Field

data class LoginData(@Field("email") val email: String, @Field("password") val password: String)
