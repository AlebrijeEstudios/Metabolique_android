package com.alebrije_estudios.metabolique.register_account.domain

import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.register_account.network.RegisterAccountRepository
import com.alebrije_estudios.metabolique.register_account.network.data.model.UserEntity
import com.alebrije_estudios.metabolique.my_profile.ui.UserModel
import java.time.LocalDate
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(private val api: RegisterAccountRepository) {
    suspend operator fun invoke(userModel: UserModel):AuthData? =
        api.registerAccount(
            UserEntity(
                email = userModel.email,
                sex = userModel.gender,
                username = userModel.name,
                password = userModel.password,
                birthDate =userModel.birthDate.getYearMountDay(),
                stature = userModel.status,
                weight = userModel.weight,
                protocolToFollow = userModel.protocolToFollow
            )
        )
}

fun LocalDate.getYearMountDay(): String {
    return "${year}-${monthValue.toString().padStart(2,'0')}-${dayOfMonth.toString().padStart(2,'0')}"
}


