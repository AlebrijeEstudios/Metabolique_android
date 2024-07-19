package com.alebrije_estudios.metabolique.my_profile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.composable.DefaultButton
import com.alebrije_estudios.metabolique.composable.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.composable.DefaultTextField
import com.alebrije_estudios.metabolique.login.ui.HeaderLogo
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
class SelectableNow:SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis < Date().time - 86400000
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun MyProfileScreenPreview() {
    MyProfileScreen(viewModel = MyProfileViewModel(), "Yote95", "Yote95@gmail.com", rememberNavController())
}

@Composable
fun MyProfileScreen(
    viewModel: MyProfileViewModel,
    name: String,
    email: String,
    navController: NavController
) {
    val isEnabled by viewModel.isEnabled.observeAsState(false)
    val gender: String by viewModel.gender.observeAsState("")
    val dateBirth: Date? by viewModel.dateBirth.observeAsState(null)
    val stature: Float by viewModel.stature.observeAsState(0f)
    val protocol: String by viewModel.protocol.observeAsState("")
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HeaderLogo(modifier = Modifier.size(128.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = name)
            Text(text = email)
            Spacer(modifier = Modifier.size(16.dp))
            DatePickerScreen(date = dateBirth) {
                viewModel.onValidated(it, gender, stature, protocol)
            }
            GenderDropdown(gender) {
                viewModel.onValidated(dateBirth, it, stature, protocol)
            }
            Spacer(modifier = Modifier.size(8.dp))
            StatureDropdown(stature,viewModel.statures) {
                viewModel.onValidated(dateBirth, gender, it, protocol)
            }
            Spacer(modifier = Modifier.size(8.dp))
            ProtocolDropdown(protocol, viewModel.protocols) {
                viewModel.onValidated(dateBirth, gender, stature, it)
            }
            Spacer(modifier = Modifier.size(64.dp))
            ButtonSave(isEnabled) {
                viewModel.saveDataUser(navController)
            }
        }
    }
}

@Composable
fun ProtocolDropdown(protocol: String, listProtocol: List<String>, onChanged: (String) -> Unit) {
    DefaultDropdownMenu(
        value = protocol,
        label = stringResource(id = R.string.label_protocol),
        dropItemsText = listProtocol
    ) {
        onChanged(it)
    }
}

@Composable
fun StatureDropdown(stature: Float, listStatures: List<String>, onChanged: (Float) -> Unit) {
    val value = if (stature > 0) stature.toString() else ""
    DefaultDropdownMenu(
        value = value,
        label = stringResource(id = R.string.label_stature),
        suffix = {
            Text("M")
        },
        dropItemsText = listStatures
    ) {
        onChanged(it.toFloat())
    }
}

@Composable
fun ButtonSave(
    isEnabled: Boolean,
    onClicked: () -> Unit
) {
    DefaultButton(label = stringResource(id = R.string.button_save_change), enabled = isEnabled) {
        onClicked()
    }
}

@Composable
fun GenderDropdown(gender: String, onChanged: (String) -> Unit) {
    //DatePickerScreen()
    DefaultDropdownMenu(
        value = gender, stringResource(id = R.string.label_gender),
        dropItemsText = listOf(
            stringResource(id = R.string.text_gender_male),
            stringResource(id = R.string.text_gender_female),
            stringResource(id = R.string.text_gender_none)
        )
    ) {
        onChanged(it)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(
    modifier: Modifier = Modifier,
    date: Date?,
    onChanged: (Date) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        yearRange = 1900..Date().year+1900,
        selectableDates = SelectableNow()
    )
    datePickerState.displayedMonthMillis = Date("2000/01/01").time
    val value = date?.toDateString()?:""
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    DefaultTextField(
        modifier = modifier.clickable {
            showDialog = true
        },
        value = value,
        enabled = false,
        trailingIcon = {
            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "")
        },
        label = stringResource(R.string.label_date_birth)
    ) {}
    if (showDialog)
        DatePickerDialog(onDismissRequest = { showDialog = false }, confirmButton = {
            Button(onClick = {
                showDialog = false
                datePickerState.selectedDateMillis?.let{
                    onChanged(Date(it+86400000))
                }
            }) {
                Text(stringResource(R.string.label_confirm))
            }
        }) {
            DatePicker(
                state = datePickerState
            )
        }
}

private fun Date.toDateString(): String {
    return "${if(date <10) "0$date" else date}/${if(month+1 <10) "0${month+1}" else month+1}/${year+1900}"
}


