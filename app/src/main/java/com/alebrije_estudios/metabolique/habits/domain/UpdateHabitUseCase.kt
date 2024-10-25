package com.alebrije_estudios.metabolique.habits.domain

import android.util.Log
import com.alebrije_estudios.metabolique.habits.data.HabitsRepository
import com.alebrije_estudios.metabolique.habits.data.network.model.CigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepCigarsEntity
import com.alebrije_estudios.metabolique.habits.data.network.model.SleepEntity
import com.alebrije_estudios.metabolique.habits.ui.HabitsModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(private val repository:HabitsRepository) {
    suspend operator fun invoke(authData: AuthData,localDate:LocalDate,habitsModel:HabitsModel):Map<String,String>{
        val sleepCigarsEntity = SleepCigarsEntity(
            accountID = authData.accountID,
            dateRegister =  localDate.getYearMountDay(),
            cigarID = habitsModel.cigarID,
            cigarsConsumed = habitsModel.cigarsConsumed,
            emotionState = habitsModel.emotionState,
            sleepID = habitsModel.sleepID,
            sleepHours = habitsModel.sleepHours,
            perceptionOfRelaxation = habitsModel.perceptionOfRelaxation
        )
        if(sleepCigarsEntity.cigarID.isNotBlank() && sleepCigarsEntity.sleepID.isNotBlank()){
            //repository.updateCigar(authData.token, cigar)
            repository.updateSleep(authData.token, sleepCigarsEntity)
            Log.i("Metabolique", "Updated sleep and cigar=$sleepCigarsEntity, authData=${authData}")
            return mapOf()
        }
       // repository.addCigar(authData.token,cigar)
        return repository.addSleep(authData.token, sleepCigarsEntity)
        //Log.i("Metabolique", "Added sleep and cigar=$sleepCigarsEntity, authData=${authData}")
    }
}