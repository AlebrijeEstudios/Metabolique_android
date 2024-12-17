package com.alebrije_estudios.metabolique.login.data.network

import android.util.Log
import android.widget.Toast
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
                AuthData(response.body()?.auth?.token!!, response.body()?.auth?.accountID!!)
            else
                null
        }
    }
    suspend fun getAccount(authData: AuthData): AccountData?{
        return withContext(Dispatchers.IO) {
            val response = loginClient.getProfile(authData.token,authData.accountID)
            Log.i("Metabolique", response.body().toString())
            Log.i("Metabolique", response.body()?.account.toString())
            /*Log.i("Metabolique", response.message())
            Log.i("Metabolique", response.errorBody()?.string()?:"Error al iniciar sesión")*/
            if(!response.isSuccessful) Log.e("Metabolique", "ErrorMessage:${response.errorBody()?.string()?:"Error al iniciar sesión"}")
            response.body()?.account
        }
    }
}