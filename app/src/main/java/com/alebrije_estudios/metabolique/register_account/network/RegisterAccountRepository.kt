package com.alebrije_estudios.metabolique.register_account.network

import android.util.Log
import com.alebrije_estudios.metabolique.login.data.network.LoginClient
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.register_account.network.data.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterAccountRepository @Inject constructor(private val loginClient: LoginClient) {
    suspend fun registerAccount(user:UserEntity):AuthData? {
        return withContext(Dispatchers.IO){
            Log.i("Metabolique", "Registering account with user: $user")
            val response = loginClient.registerAccount(user)
            Log.i("Metabolique", response.message())
            Log.e("Metabolique", response.errorBody()?.string()?:"Error al registrar cuenta")
            Log.i("Metabolique", response.body()?.auth.toString())
            if(response.body()?.auth?.token == null || response.body()?.auth?.accountID == null)
                null
            else
                AuthData(response.body()?.auth?.token!!, response.body()?.auth?.accountID!!)
        }
    }
}