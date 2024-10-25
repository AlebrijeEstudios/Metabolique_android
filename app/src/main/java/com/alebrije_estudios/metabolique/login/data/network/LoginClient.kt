package com.alebrije_estudios.metabolique.login.data.network


import com.alebrije_estudios.metabolique.core.di.API_KEY
import com.alebrije_estudios.metabolique.login.data.network.model.AccountStateResponse
import com.alebrije_estudios.metabolique.login.data.network.model.Date
import com.alebrije_estudios.metabolique.login.data.network.model.LoginResponse
import com.alebrije_estudios.metabolique.login.data.network.model.LoginData
import com.alebrije_estudios.metabolique.register_account.network.data.model.UserEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LoginClient {
    @Headers("Metabolique_API_KEY: $API_KEY")
    @POST("auth/login")
    suspend fun doLogin(@Body user: LoginData): Response<LoginResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @POST("auth/forgot-password")
    suspend fun forgotPassword(email: String): Response<AccountStateResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @POST("accounts/account-profile")
    suspend fun registerAccount(@Body user:UserEntity): Response<LoginResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @PUT("accounts")
    suspend fun editAccount(
        accountID:String,
        username: String,
        email: String,
        //password: String,
        birthDate: Date,
        sex: String,
        status: Int,
        weight: Int,
        protocolToFollow: String
    ): Response<AccountStateResponse>

    @Headers("Metabolique_API_KEY: $API_KEY")
    @DELETE("/accounts/account-profile/{id}")
    suspend fun deleteAccount(@Path("id") id:String): Response<AccountStateResponse>
}