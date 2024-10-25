package com.alebrije_estudios.metabolique.medication.ui

import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPUTData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsPostData
import com.alebrije_estudios.metabolique.medication.data.network.model.TimesData
import com.alebrije_estudios.metabolique.register_account.domain.getYearMountDay
import java.time.LocalDate
import java.time.LocalTime

data class MedicationModel(var medicationID:String = "",var periodID: String = "", var name: String, var amount:String,var dateStart:LocalDate, var dateEnd:LocalDate ,var listSchedules:List<Schedule>){
    fun toEntity(authData: AuthData,date: LocalDate): MedicationsData {
        var frequency = ""
        listSchedules.forEach { schedule ->
            frequency += "${schedule.hour},"
        }
        frequency = frequency.dropLast(1)  // Remove last comma
        return MedicationsData(
            accountID = authData.accountID,
            medicationID = medicationID,
            dosage = amount,
            startDate = dateStart.getYearMountDay(),
            endDate = dateEnd.getYearMountDay(),
            medicationName = name,
            //frequency = frequency,
            times = listSchedules.map {
                TimesData(
                    timeID = "",
                    periodID = "",
                    date = date.getYearMountDay(),
                    time = it.hour.toString(),
                    medicationStatus = it.isConsumed
                )
            }
        )
    }

    fun toEntityPost(authData: AuthData, localDate: LocalDate): MedicationsPostData {
        var frequency = ""
        listSchedules.forEach { schedule ->
            frequency += "${schedule.hour},"
        }
        frequency = frequency.dropLast(1)
        return MedicationsPostData(
            accountID = authData.accountID,
            dateActual = localDate.getYearMountDay(),
            medicationID = medicationID,
            dosage = amount,
            startDate = dateStart.getYearMountDay(),
            endDate = dateEnd.getYearMountDay(),
            medicationName = name,
            times = frequency
        )
    }

    fun toEntityPUT(authData: AuthData, localDate: LocalDate): MedicationsPUTData {
        var frequency = ""
        listSchedules.forEach { schedule ->
            frequency += "${schedule.hour},"
        }
        frequency = frequency.dropLast(1)
        return MedicationsPUTData(
            periodID = periodID ,
            date = localDate.getYearMountDay(),
            dosage = amount,
            startDate = dateStart.getYearMountDay(),
            endDate = dateEnd.getYearMountDay(),
            medicationName = name,
            times = listSchedules.map {
                TimesData(
                    timeID = it.ScheduleID,
                    periodID = periodID,
                    date = localDate.getYearMountDay(),
                    time = it.hour.toString(),
                    medicationStatus = it.isConsumed
                )
            },
            frequency = frequency
        )
    }

    data class Schedule(var ScheduleID:String = "", var periodID:String = "", var hour:LocalTime, var isConsumed:Boolean = false)
}

