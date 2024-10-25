package com.alebrije_estudios.metabolique.login.data.network.model

import com.google.gson.annotations.SerializedName
//data class ResponseLogin(@SerializedName("response") val response:LoginResponse)
data class LoginResponse(@SerializedName("message") val message: String, @SerializedName("auth") val auth: AuthData)


