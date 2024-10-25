package com.alebrije_estudios.metabolique.register_account.network.data.model

import retrofit2.http.Field


data class UserEntity(
    @Field("username") val username: String,
    @Field("email") val email: String,
    @Field("password") val password: String,
    @Field("birthDate") val birthDate: String,
    @Field("sex") val sex: String,
    @Field("stature") val stature: Number,
    @Field("weight") val weight: Number,
    @Field("protocolToFollow") val protocolToFollow: String
)
