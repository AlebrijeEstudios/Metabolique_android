package com.alebrije_estudios.metabolique.login.data.network.model

import com.google.gson.annotations.SerializedName

data class AccountStateResponse(@SerializedName("message") val message: String, @SerializedName("status") val status: String)
