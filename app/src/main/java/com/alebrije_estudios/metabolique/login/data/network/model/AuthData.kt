package com.alebrije_estudios.metabolique.login.data.network.model

import com.google.gson.annotations.SerializedName

data class AuthData(@SerializedName("token") private val _token: String, @SerializedName("accountID") val accountID: String){
    val token get() = "Bearer $_token"
}
