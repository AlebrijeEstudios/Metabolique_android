package com.alebrije_estudios.metabolique.login.data.network.model

import com.google.gson.annotations.SerializedName

data class AccountData(
    @SerializedName("accountID") val accountID:String,
    @SerializedName("username") val username:String,
    @SerializedName("email") val email:String,
    @SerializedName("birthdate") val birthdate: String,
    @SerializedName("sex") val gender: String,
    @SerializedName("stature") val name: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("protocolToFollow") val protocolToFollow:String
)
