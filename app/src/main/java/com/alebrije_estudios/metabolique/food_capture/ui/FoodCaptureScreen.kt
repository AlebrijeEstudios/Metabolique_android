package com.alebrije_estudios.metabolique.food_capture.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.feed.ui.getDayMonthValueYear
import com.alebrije_estudios.metabolique.my_profile.ui.ButtonSave
import java.time.LocalDate
import java.time.LocalTime

@Preview(showBackground = true, locale = "es")
@Composable
fun FoodCaptureScreenPreview() {
    FoodCaptureScreen(FoodCaptureViewModel())
}

@Composable
fun FoodCaptureScreen(viewModel: FoodCaptureViewModel) {
    val listFoods = listOf("Huevo", "Huevo", "Huevo")
    Column(Modifier.fillMaxWidth()) {
        HeaderDateTime(viewModel.date, viewModel.time)
        NameText()
        SearchFood()
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(Modifier.weight(1f)) {
            items(listFoods) { food ->
                ItemFood(food)
                HorizontalDivider()
            }
        }
        LevelSatisfactionDropdown()
        Spacer(modifier = Modifier.size(8.dp))
        LinkedEmotionsDropdown()
        Spacer(modifier = Modifier.size(8.dp))
        DefaultButton(label = "", enabled = true) {

        }
        DefaultButton(label = "", enabled = false) {

        }
        Spacer(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun LinkedEmotionsDropdown() {
    DefaultDropdownMenu(value = "", label = "", dropItemsText = listOf("")) {

    }
}

@Composable
fun LevelSatisfactionDropdown() {
    DefaultDropdownMenu(value = "", label = "", dropItemsText = listOf("")) {

    }
}

@Composable
fun ItemFood(food: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(food)
        Text(food)
        Text(food)
    }
}

@Composable
fun SearchFood() {
    OutlinedTextField(
        value = "",
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        onValueChange = {},
        placeholder = {
            Text("")
        },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "")
        }
    )
}

@Composable
fun NameText() {
    Text("")
}


@Composable
fun HeaderDateTime(date: LocalDate, time: LocalTime) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(date.getDayMonthValueYear())
        Spacer(modifier = Modifier.weight(1f))
        Text(time.toFormat12())
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_date),
                contentDescription = ""
            )
        }
        TimePickerDialog(false, {}) {}
    }
}

fun LocalTime.toFormat12(): String {
    val module = this.hour%12
    return "${if(module == 0)"12" else hour%12}:${minute.toString().padStart(2,'0')} ${if(hour >=12) "PM" else "AM"}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    showDialog: Boolean = true,
    onDismissRequest: () -> Unit,
    onChanged: (LocalTime) -> Unit
) {
    val stateTime = rememberTimePickerState()
    if (showDialog) {
        DatePickerDialog(onDismissRequest = { onDismissRequest() }, confirmButton = {
            ButtonSave(isEnabled = true) {
                onChanged(LocalTime.of(stateTime.hour, stateTime.minute))
            }
        }) {
            TimePicker(modifier = Modifier.align(Alignment.CenterHorizontally), state = stateTime)
        }
    }
}
