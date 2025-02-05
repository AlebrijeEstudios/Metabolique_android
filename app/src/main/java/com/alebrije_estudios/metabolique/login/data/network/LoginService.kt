package com.alebrije_estudios.metabolique.login.data.network

import android.app.Activity.MODE_PRIVATE
import android.util.Log
import com.alebrije_estudios.metabolique.login.data.network.model.AccountData
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.data.network.model.LoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject


class LoginService @Inject constructor(private val loginClient: LoginClient) {

    suspend fun doLogin(user:String, password:String): AuthData? {
        return withContext(Dispatchers.IO) {
            val response = loginClient.doLogin(LoginData(user, password))
            Log.i("Metabolique", response.body().toString())
            Log.i("Metabolique", response.body()?.auth.toString())
            /*Log.i("Metabolique", response.message())
            Log.i("Metabolique", response.errorBody()?.string()?:"Error al iniciar sesión")*/
            if(!response.isSuccessful)
            {
                Log.e("Metabolique", "ErrorMessage:${response.errorBody()?.string()?:"Error al iniciar sesión"}")
            }
            if(response.body()?.auth?.token != null && response.body()?.auth?.accountID != null)
                AuthData(
                    response.body()?.auth?.token!!,
                    response.body()?.auth?.accountID!!,
                    refreshToken = response.body()?.auth?.refreshToken!!
                )
            else
                null
        }
    }

    suspend fun getAccount(authData: AuthData): AccountData?{
        return withContext(Dispatchers.IO) {
            val newAuthData =refreshToken(authData)
            if(newAuthData == null) {
                Log.e("Metabolique", "Token no válido")
                return@withContext null
            }
            val response = loginClient.getProfile(newAuthData.token,authData.accountID)
            Log.i("Metabolique", response.body().toString())
            Log.i("Metabolique", response.body()?.account.toString())
            /*Log.i("Metabolique", response.message())
            Log.i("Metabolique", response.errorBody()?.string()?:"Error al iniciar sesión")*/
            if(!response.isSuccessful) Log.e("Metabolique", "ErrorMessage:${response.errorBody()?.string()?:"Error al iniciar sesión"}")
            response.body()?.account
        }
    }
    
    suspend fun refreshToken(authData: AuthData): AuthData?{
        return withContext(Dispatchers.IO) {
            val response = loginClient.refreshToken(authData)
            Log.i("Metabolique", response.body().toString())
            Log.i("Metabolique", response.body()?.auth.toString())
            /*Log.i("Metabolique", response.message())
            Log.i("Metabolique", response.errorBody()?.string()?:"Error al iniciar sesión")*/
            if(!response.isSuccessful) Log.e("Metabolique", "ErrorMessage:${response.errorBody()?.string()?:"Error al iniciar sesión"}")
            if(response.body()?.auth?.token != null && response.body()?.auth?.accountID != null){
                val authData = AuthData(
                    response.body()?.auth?.token!!,
                    response.body()?.auth?.accountID!!,
                    refreshToken = response.body()?.auth?.refreshToken!!
                )
                authData
            }
            else
                null
        }
    }

    suspend fun deleteAccount(authData: AuthData): Boolean{
        return withContext(Dispatchers.IO) {
            val response = loginClient.deleteAccount(authData.accountID,  authData.token)
            Log.i("Metabolique", response.body().toString())
            Log.i("Metabolique", response.body()?.message.toString())
            /*Log.i("Metabolique", response.message())
            Log.i("Metabolique", response.errorBody()?.string()?:"Error al iniciar sesión")*/
            if(!response.isSuccessful) Log.e("Metabolique", "ErrorMessage:${response.errorBody()?.string()?:"Error al iniciar sesión"}")
            response.isSuccessful
        }
    }
}