package com.alebrije_estudios.metabolique.medication.data.network

import com.alebrije_estudios.metabolique.medication.data.network.model.GraphicData
import com.alebrije_estudios.metabolique.medication.data.network.model.MedicationsData
import com.google.gson.annotations.SerializedName

data class MedicationGetResponse(val message: String, @SerializedName("medications") val medicationList:List<MedicationsData>, @SerializedName("weeklyAttachments") val weeklyAttachments:List<GraphicData> )

data class MedicationPostResponse(val message: String, val medicationList:MedicationsData, val weeklyAttachments:List<GraphicData>)

data class MedicationDeleteResponse(val message: String, val status:String)