package com.alebrije_estudios.metabolique.login.data.network.model

import android.content.SharedPreferences
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class AuthData(
    @Field("accessToken") @SerializedName("accessToken") private var _token: String,
    @Field("accountID") @SerializedName("accountID") val accountID: String,
    @Field("refreshToken") @SerializedName("refreshToken") var refreshToken: String,
) {
    val token get() = "Bearer $_token"
    private var sharedPref: SharedPreferences? = null
    companion object {
        fun createAuthData(sharedPref: SharedPreferences): AuthData? {
            if (!sharedPref.getString("token", "")?.contains(".")!!) {
                return null
            }
            val authData  = AuthData(
                _token = sharedPref.getString("token", "") ?: "",
                accountID = sharedPref.getString("accountID", "") ?: "",
                refreshToken = sharedPref.getString("refreshToken", "") ?: "",
            )
            authData.sharedPref = sharedPref
            return authData
        }
    }
    fun createPreferences(sharedPref:SharedPreferences? = null ) {
        /* Create Preferences */
        if(this.sharedPref == null) this.sharedPref = sharedPref
        with(this.sharedPref!!.edit()) {
            putString("token", _token)
            putString("accountID", accountID)
            putString("refreshToken", refreshToken)
            commit()
        }
    }
}

