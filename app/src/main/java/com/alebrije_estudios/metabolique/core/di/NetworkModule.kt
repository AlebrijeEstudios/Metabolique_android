package com.alebrije_estudios.metabolique.core.di

import com.alebrije_estudios.metabolique.exercise.data.network.ExerciseClient
import com.alebrije_estudios.metabolique.habits.data.network.HabitsClient
import com.alebrije_estudios.metabolique.login.data.network.LoginClient
import com.alebrije_estudios.metabolique.medication.data.network.MedicationClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_KEY = "0e6b2066-9e98-4783-8c82-c3530aa8a197"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
        .baseUrl("https://metaboliqueapp.azurewebsites.net/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit): LoginClient = retrofit.create(LoginClient::class.java)

    @Singleton
    @Provides
    fun provideExercise(retrofit: Retrofit): ExerciseClient = retrofit.create(ExerciseClient::class.java)

    @Singleton
    @Provides
    fun provideHabitsClient(retrofit: Retrofit): HabitsClient = retrofit.create(HabitsClient::class.java)

    @Singleton
    @Provides
    fun provideMedicationClient(retrofit: Retrofit): MedicationClient = retrofit.create(MedicationClient::class.java)
}