package com.alebrije_estudios.metabolique.medication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.exercise.ui.AddButton
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.ui.DefaultTextField
import com.alebrije_estudios.metabolique.feed.ui.DateSelected
import com.alebrije_estudios.metabolique.feed.ui.GraphicBars
import com.alebrije_estudios.metabolique.feed.ui.MonthlyFollowUp
import com.alebrije_estudios.metabolique.food_capture.ui.TimePickerDialog
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.my_profile.ui.ButtonSave
import com.alebrije_estudios.metabolique.my_profile.ui.DatePickerScreen
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.DefaultTimePicker
import com.alebrije_estudios.metabolique.ui.theme.BackgroundColor
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalDate
import java.time.LocalTime


/*@Preview(showBackground = true, locale = "es")
@Composable
fun MedicationScreenPreview() {
    MedicationScreen(MedicationViewModel(), rememberNavController())
}*/

@Composable
fun MedicationScreen(
    medicationViewModel: MedicationViewModel,
    authData: AuthData,
    navController: NavController
) {
    val showAddMedication: Boolean by medicationViewModel.showAddMedication.observeAsState(false)
    val showRegisterAftereffect: Boolean by medicationViewModel.showRegisterAftereffect.observeAsState(initial = false)
    val showMessageDelete: Boolean by medicationViewModel.showMessageDelete.observeAsState( false)
    val localDate: LocalDate by medicationViewModel.localDate.observeAsState(initial = LocalDate.now())
    val medication:MedicationModel by medicationViewModel.medication.observeAsState(MedicationModel(name = "", amount =  "", dateStart =  localDate, dateEnd = localDate, listSchedules = listOf()))
    RegisterAftereffect(showRegisterAftereffect, {
        medicationViewModel.closeRegisterAftereffectScreen()
    }) {}
    DialogDeleteMedication(showMessageDelete, {
        medicationViewModel.hiddenDeleteDialog()
    }, {
        medicationViewModel.deleteMedication(authData,medication)
    })
    AddMedication(
        showDialog =showAddMedication,
        onDismissRequest = { medicationViewModel.closeAddMedicationScreen() },
        onSaveChangesRequest = {
            if (medication.medicationID.isBlank())
                medicationViewModel.addMedication(authData,medication)
            else
                medicationViewModel.updateMedication(authData,medication)
            medicationViewModel.closeAddMedicationScreen()
        },
        medicationModel = medication,
        onDelete = {
            medicationViewModel.showDeleteDialog()
        }
    )
    LazyColumn(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            DateSelected(date = localDate) {
                medicationViewModel.changeDate(authData,it)
            }
            Spacer(Modifier.size(8.dp))
            GraphicBars(
                stringResource(id = R.string.label_title_medications),
                mapOf("G" to 0, "F" to 0, "A" to 0, "T" to 0, "D" to 0, "C" to 0, "B" to 0)
            )
            Spacer(Modifier.size(8.dp))
            MonthlyFollowUp(isChecked = false) { navController.navigate(Screen.MonthlyMonitoringMedication.route)}
            Spacer(Modifier.size(8.dp))
        }
        if (medicationViewModel.listMedications.isNotEmpty()) {
            items(medicationViewModel.listMedications) { medication ->
                MedicationItem(medication, onUpdate = {
                    medicationViewModel.changeMedication(medication)
                    medicationViewModel.showAddMedicationScreen()
                })
            }
        } else {
            item {
                Text(
                    text = stringResource(id = R.string.text_message_no_medications),
                    style = Typography.bodyLarge,
                    color = LabelColor,
                )
            }
        }
        item {
            Spacer(Modifier.size(8.dp))
            AddButton(label = stringResource(id = R.string.button_add_medication)) {
                medicationViewModel.changeMedication(MedicationModel(name = "", amount =  "", dateStart =  localDate, dateEnd = localDate, listSchedules = listOf()))
                medicationViewModel.showAddMedicationScreen()
            }
            Spacer(Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.text_message_aftereffect),
                style = Typography.bodyLarge,
                color = LabelColor,
            )
            Text(
                modifier = Modifier.clickable { medicationViewModel.showRegisterAftereffectScreen()},
                text = stringResource(id = R.string.label_register_here),
                style = Typography.bodyLarge,
                color = PrimaryColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Spacer(Modifier.size(16.dp))
        }
    }
}

@Composable
fun DialogDeleteMedication(show: Boolean, onDismissRequest: () -> Unit, onDelete: () -> Unit ) {
    if (show) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeDefaults.Medium)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.text_message_delete_medication),
                    style = Typography.bodyLarge,
                    color = LabelColor,
                )
                Spacer(Modifier.size(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    DefaultButton(
                        label = stringResource(id = R.string.button_cancel),
                        modifier = Modifier,
                        onClicked = { onDelete() }
                    )
                    Spacer(Modifier.size(8.dp))
                    DefaultButton(
                        label = stringResource(id = R.string.button_delete),
                        modifier = Modifier,
                        onClicked = { onDelete() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAftereffect(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onSaveChangesRequest: (AftereffectModel) -> Unit
) {
    if (showDialog) {
        var timeStart: LocalTime? by remember { mutableStateOf(null) }
        var timeEnd: LocalTime? by remember { mutableStateOf(null) }
        var description: String by remember { mutableStateOf("") }
        ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                OutlinedTimePicker(timeStart, stringResource(id = R.string.label_time_start)) {
                    timeStart = it
                }
                OutlinedTimePicker(timeEnd, stringResource(id = R.string.label_time_end)) {
                    timeEnd = it
                }
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(id = R.string.label_register_aftereffect)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4
                )
                ButtonSave(isEnabled = true) {
                    onSaveChangesRequest(
                        AftereffectModel(
                            timeStart!!,
                            timeEnd!!,
                            description
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun OutlinedTimePicker(time: LocalTime?, label: String, onChanged: (LocalTime) -> Unit) {
    var showDialog: Boolean by remember { mutableStateOf(false) }
    DefaultTextField(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            showDialog = true
        },
        value = time?.toString() ?: "", label = label, trailingIcon = {
            Icon(imageVector = Icons.Default.Alarm, contentDescription = "")
        }) {}
    TimePickerDialog(showDialog, { showDialog = false }) {
        onChanged(it)
    }

}

@Composable
fun MedicationItem(medicationModel: MedicationModel, onUpdate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, LabelColor, shape = ShapeDefaults.Large)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .clip(ShapeDefaults.Medium)
                .background(PrimaryColor)
                .padding(12.dp)
                .clickable {
                    onUpdate()
                }
        ) {
            Text(
                text = "${medicationModel.name} ${medicationModel.amount}",
                style = Typography.titleLarge,
                color = DefaultColor
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_date),
                tint = DefaultColor,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        medicationModel.listSchedules.forEach { schedule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = schedule.hour.toString(),
                    style = Typography.bodyLarge,
                    color = PrimaryColor,
                    fontWeight = FontWeight.SemiBold
                )
                if (schedule.isConsumed)
                    Icon(
                        modifier = Modifier.clickable { schedule.isConsumed = false },
                        imageVector = Icons.Filled.CheckCircle,
                        tint = BackgroundColor,
                        contentDescription = ""
                    )
                else
                    Icon(
                        modifier = Modifier.clickable { schedule.isConsumed = true },
                        imageVector = Icons.Default.RadioButtonUnchecked,
                        tint = Color.LightGray,
                        contentDescription = ""
                    )
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedication(
    showDialog: Boolean,
    medicationModel: MedicationModel,
    onDismissRequest: () -> Unit,
    onSaveChangesRequest: (MedicationModel) -> Unit,
    onDelete: (MedicationModel) -> Unit
) {
    if (showDialog) {
        var name: String by remember { mutableStateOf(medicationModel.name) }
        var amount: String by remember { mutableStateOf(medicationModel.amount) }
        var dateStart: LocalDate? by remember { mutableStateOf(medicationModel.dateStart) }
        var dateEnd: LocalDate? by remember { mutableStateOf(medicationModel.dateEnd) }
        var numberSchedule: Int by remember { mutableIntStateOf(medicationModel.listSchedules.size) }
        val listScheduleConsumer: MutableList<LocalTime> =
            remember { mutableStateListOf() }
        if (medicationModel.medicationID.isNotEmpty() && listScheduleConsumer.size != medicationModel.listSchedules.size) {
            listScheduleConsumer.clear()
            medicationModel.listSchedules.forEach {
                listScheduleConsumer.add(it.hour)
            }
        }
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            containerColor = DefaultColor,
        ) {
            LazyColumn(
                modifier = Modifier
                    .scrollable(rememberScrollState(), Orientation.Vertical)
                    .padding(16.dp)
            ) {
                item {
                    IconButton(modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.End), onClick = { onDismissRequest() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    }
                    DefaultTextField(
                        value = name,
                        label = stringResource(id = R.string.label_name_medication),
                    ) {
                        name = it
                    }
                    DefaultTextField(
                        value = amount,
                        label = stringResource(id = R.string.label_dose),
                    ) {
                        amount = it
                    }
                    DatePickerScreen(
                        label = stringResource(id = R.string.label_date_init),
                        date = dateStart
                    ) {
                        dateStart = it
                    }
                    DatePickerScreen(
                        label = stringResource(id = R.string.label_date_end),
                        date = dateEnd
                    ) {
                        dateEnd = it
                    }
                    DefaultDropdownMenu(
                        value = if (numberSchedule > 0) "$numberSchedule" else "",
                        label = stringResource(id = R.string.label_daily_frequency),
                        dropItemsText = listOf("1", "2", "3", "4", "5", "6", "7")
                    ) {
                        numberSchedule = it.toIntOrNull() ?: 1
                        listScheduleConsumer.clear()
                        repeat(numberSchedule) {
                            listScheduleConsumer.add(LocalTime.now())
                        }
                    }
                    repeat(numberSchedule) { i ->
                        DefaultTimePicker(
                            value = listScheduleConsumer[i],
                            label = stringResource(id = R.string.label_schedule) + " ${i + 1}",
                        ) {
                            listScheduleConsumer[i] = it
                        }
                    }
                    DefaultButton(label = stringResource(id = R.string.button_save_change)) {
                        medicationModel.name = name
                        medicationModel.amount = amount
                        medicationModel.dateStart = dateStart!!
                        medicationModel.dateEnd = dateEnd!!
                        val aux = medicationModel.listSchedules
                        medicationModel.listSchedules = if (aux.size > listScheduleConsumer.size) {
                            aux.subList(0, listScheduleConsumer.size)

                        } else if (aux.size < listScheduleConsumer.size) {
                            aux.plus(
                                listScheduleConsumer.subList(
                                    aux.size,
                                    listScheduleConsumer.size
                                ).map {
                                    MedicationModel.Schedule(
                                        hour = it
                                    )
                                })
                        } else {
                            aux
                        }
                        onSaveChangesRequest(medicationModel)
                    }
                    if (medicationModel.medicationID.isNotEmpty())
                        DefaultButton(label = stringResource(id = R.string.button_delete)) {
                            onDelete(medicationModel)
                        }
                }
            }
        }
    }
}
