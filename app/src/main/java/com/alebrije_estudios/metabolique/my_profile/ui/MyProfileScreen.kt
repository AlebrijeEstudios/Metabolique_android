package com.alebrije_estudios.metabolique.my_profile.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.ui.DefaultTextField
import com.alebrije_estudios.metabolique.feed.ui.getDayMonthValueYear
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.ui.HeaderLogo
import com.alebrije_estudios.metabolique.ui.DefaultDialog
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.RedColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
class SelectableNow : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis < System.currentTimeMillis()
    }
}

//@Preview(showBackground = true, locale = "es")
//@Composable
//fun MyProfileScreenPreview() {
//    val prueba =  MyProfileViewModel(RegisterAccountUseCase(RegisterAccountRepository(LoginClient())))
//    //prueba.onValidated(System.currentTimeMillis(),"",1.6f,"")
//    MyProfileScreen(viewModel = prueba, "Yote95", "Yote95@gmail.com","", rememberNavController())
//}

@Composable
fun MyProfileScreen(
    viewModel: MyProfileViewModel,
    name: String,
    email: String,
    password: String,
    isEditUser: Boolean = false,
    navController: NavController,
    isLoading:(Boolean) -> Unit,
    doLogout:()-> Unit= {},
    authData: AuthData? = null,
    doLogin:(AuthData)-> Unit
) {
    val isEnabled by viewModel.isEnabled.observeAsState(false)
    val gender: String by viewModel.gender.observeAsState("")
    val dateBirth: LocalDate? by viewModel.dateBirth.observeAsState(null)
    val weight: Float by viewModel.weight.observeAsState(0f)
    val stature: Float by viewModel.stature.observeAsState(0f)
    val protocol: String by viewModel.protocol.observeAsState("")
    val showMessageDialog: Boolean by viewModel.showMessageDialog.observeAsState(false)
    val message: String by viewModel.message.observeAsState("")
    val showDialogDeleteAccount by viewModel.showDialogDeleteAccount.observeAsState(false )
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(
            rememberScrollState())) {
            HeaderLogo(modifier = Modifier)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = name)
            Text(text = email)
            Spacer(modifier = Modifier.size(16.dp))
            DatePickerScreen(date = dateBirth, label = stringResource(R.string.label_date_birth)) {
                viewModel.onValidated(
                    dateBirth = it,
                    gender = gender,
                    stature = stature,
                    weight = weight,
                    protocol = protocol
                )
            }
            GenderDropdown(gender) {
                viewModel.onValidated(
                    dateBirth = dateBirth,
                    gender = it,
                    stature = stature,
                    weight = weight,
                    protocol = protocol
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            StatureDropdown(stature, viewModel.statures) {
                viewModel.onValidated(
                    dateBirth = dateBirth,
                    gender = gender,
                    stature = it,
                    weight = weight,
                    protocol = protocol
                )
            }
            WeightDropdown(weight, viewModel.weights) {
                viewModel.onValidated(
                    dateBirth = dateBirth,
                    gender = gender,
                    stature = stature,
                    weight = it,
                    protocol = protocol
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            ProtocolDropdown(protocol, viewModel.protocols) {
                viewModel.onValidated(
                    dateBirth = dateBirth,
                    gender = gender,
                    stature = stature,
                    weight = weight,
                    protocol = it
                )
            }
            Spacer(modifier = Modifier.size(64.dp))
            ButtonSave(isEnabled) {
                viewModel.saveDataUser(name, email, password,{isLoading(it)}){
                    doLogin(it)
                }
            }
            if(isEditUser){
                ButtonLogout(){
                    doLogout()
                }
                ButtonDelete(){
                    viewModel.showDialogDeleteAccount()
                }
                LaunchedEffect(key1 = "MyProfile") {
                    viewModel.getAccuntData(authData!!)
                }
            }
            DialogConfirmDeleteAccount(showDialogDeleteAccount,onDismissRequest = {viewModel.hiddenDialogDeleteAccount()}) {
                viewModel.deleteDataUser(authData!!)
                doLogout()
            }
            DefaultDialog(show = showMessageDialog, title = "Error", message = message) {
                viewModel.hiddenDialogMessage()
            }
        }
    }
}

@Composable
fun DialogConfirmDeleteAccount(show:Boolean,onDismissRequest:()-> Unit,onClicked: () -> Unit) {
if(show)
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(ShapeDefaults.Medium)
                .background(color = DefaultColor)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.text_message_delete_account),
                color = TextColor,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                CancelButton()
                ConfirmButton()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ConfirmButton() {
    var count:Int by remember { mutableIntStateOf(5) }
    val timer = rememberCoroutineScope()
    if(count == 5) timer.launch {
        while (count > 0) {
            delay(1000L)
            count--
            //Log.i("Scope is" , count.toString())
        }
    }
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = RedColor,
            contentColor = DefaultColor
        ),
        enabled = count <= 0) {
        Text(
            text = stringResource(id = R.string.button_confirm) + if(count > 0)" ($count) " else "",
            style = Typography.bodyLarge,
            fontSize = 12.sp

        )
    }
}

@Composable
fun CancelButton() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = DefaultColor
        )
    ) {
        Text(
            text = stringResource(id = R.string.button_cancel),
            style = Typography.bodyLarge,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ButtonDelete(onClicked: () -> Unit) {
    DefaultButton(
        label = stringResource(id = R.string.button_delete_account), modifier = Modifier.fillMaxWidth(),
        containerColor = RedColor
    ) {
        onClicked()
    }
}

@Composable
fun ButtonLogout(onClicked: () -> Unit) {
    DefaultButton(label = stringResource(id = R.string.button_logout), modifier = Modifier.fillMaxWidth()) {
        onClicked()
    }
}

@Composable
fun WeightDropdown(weight: Float, weights: List<String>, onChanged: (Float) -> Unit) {
    DefaultDropdownMenu(
        value = if(weight > 0)weight.toString() else "",
        label = stringResource(id = R.string.label_weight),
        suffix = { Text("kg") },
        dropItemsText = weights
    ) {
        onChanged(it.toFloatOrNull() ?: 0f)
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
    DefaultButton(label = stringResource(id = R.string.button_save_change), enabled = isEnabled, modifier = Modifier.fillMaxWidth()) {
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
    label: String,
    date: LocalDate?,
    onChanged: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        yearRange = 1900..Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of("UTC"))
            .toLocalDate().year,
        selectableDates = SelectableNow()
    )
    //datePickerState.displayedMonthMillis = Date("2000/01/01").time
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    DefaultTextField(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            },
        colors = OutlinedTextFieldDefaults.colors(
            disabledSuffixColor = TextColor,
            disabledLabelColor = LabelColor,
            disabledTextColor = TextColor,
            disabledTrailingIconColor = TextColor
        ),
        value = date?.getDayMonthValueYear() ?: "",
        enabled = false,
        trailingIcon = {
            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "")
        },
        label = label,
    ) {}
    if (showDialog)
        DatePickerDialog(onDismissRequest = { showDialog = false }, confirmButton = {
            Button(onClick = {
                showDialog = false
                datePickerState.selectedDateMillis?.let {
                    onChanged(Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate())
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

//private fun Date.toDateString(): String {
//    return "${if(date <10) "0$date" else date}/${if(month+1 <10) "0${month+1}" else month+1}/${year+1900}"
//}


