package com.alebrije_estudios.metabolique.medication.data.network.model

import com.alebrije_estudios.metabolique.medication.ui.MedicationModel
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime

data class TimesData(
    @SerializedName("timeID") var timeID: String,
    @SerializedName("periodID") var periodID: String,
    @SerializedName("dateMedication") var date: String,
    @SerializedName("time") var time:String,
    @SerializedName("medicationStatus") var medicationStatus:Boolean
) {
    fun toModel():MedicationModel.Schedule {
        return MedicationModel.Schedule(
            timeID,
            periodID,
            hour = LocalTime.parse(time),
            isConsumed = medicationStatus
        )
    }
}

data class MedicationsData(
    @SerializedName("accountID") var accountID:String,
    @SerializedName("periodID") val period:String = "",
    @SerializedName("medicationID") var medicationID:String,
    @SerializedName("nameMedication") var medicationName:String,
    @SerializedName("dose")var dosage:String,
    //var frequency:String,
    //var medicationType:String,
    @SerializedName("initialFrec") var startDate:String,
    @SerializedName("finalFrec") var endDate:String,
    var times: List<TimesData>
    ) {
    fun toModel():MedicationModel {
        return MedicationModel(
            medicationID = medicationID,
            periodID = period,
            name = medicationName,
            amount = dosage,
            dateStart = LocalDate.parse(startDate),
            dateEnd = LocalDate.parse(endDate),
            listSchedules = times.map { it.toModel() }
        )
    }
}
data class MedicationsPostData(
    var accountID:String,
    val dateActual:String,
    var medicationID:String,
    @SerializedName("nameMedication") var medicationName:String,
    @SerializedName("dose")var dosage:String,
    //var medicationType:String,
    @SerializedName("initialFrec") var startDate:String,
    @SerializedName("finalFrec") var endDate:String,
    var times: String
)
data class MedicationsPUTData(
    @SerializedName("periodID") var periodID:String,
    @SerializedName("updateDate") var date:String,
    @SerializedName("nameMedication") var medicationName:String,
    @SerializedName("dose")var dosage:String,
    @SerializedName("initialFrec") var startDate:String,
    @SerializedName("finalFrec") var endDate:String,
    @SerializedName("times") var times: List<TimesData>,
    @SerializedName("newTimes") var frequency:String,
)