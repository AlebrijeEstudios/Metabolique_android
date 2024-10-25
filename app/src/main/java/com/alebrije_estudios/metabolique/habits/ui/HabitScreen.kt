package com.alebrije_estudios.metabolique.habits.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.exercise.ui.AddButton
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.feed.ui.DateSelected
import com.alebrije_estudios.metabolique.feed.ui.GraphicBars
import com.alebrije_estudios.metabolique.feed.ui.MonthlyFollowUp
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenuCheckList
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenuCheckListMoreLabel
import com.alebrije_estudios.metabolique.ui.NonLazyVerticalGrid
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalDate


//@Preview(showBackground = true, locale = "es")
//@Composable
//fun HabitsScreenPreview() {
//    HabitsScreen(HabitViewModel(), rememberNavController())
//}

@Composable
fun HabitsScreen(habitViewModel: HabitViewModel, authData: AuthData, navController: NavController) {
    //val listDrinks = habitViewModel.getListDrinks(authData)
    //habitViewModel.getHabits(authData)
    val cigarsConsumed: String by habitViewModel.cigarsConsumed.observeAsState(initial = "")
    val emotionalState: List<String> = habitViewModel.emotionalState
    val hoursSleep: String by habitViewModel.hoursSleep.observeAsState(initial = "")
    val perceptionRest: String by habitViewModel.perceptionRest.observeAsState("")
    val showDialogAddDrink by habitViewModel.showDialogAddDrink.observeAsState(initial = false)
    val localDate: LocalDate by habitViewModel.localDate.observeAsState(initial = LocalDate.now())
    val drink: DrinksModel by habitViewModel.drink.observeAsState(initial = DrinksModel("", "", -1))
    AddDrink(
        drink,
        showDialogAddDrink,
        habitViewModel.listDrinks,
        habitViewModel.listCups,
        { habitViewModel.hiddenDialogAddDrink() }) { habitViewModel.addDrink(authData, it) }
    LazyColumn(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TitleView(text = stringResource(id = R.string.title_habits))
        item {
            DateSelected(date = localDate) {
                habitViewModel.onDateChanged(authData, it)
            }
            GraphicBars(
                stringResource(id = R.string.label_title_sleepy),
                mapOf("" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
            )
            MonthlyFollowUp(modifier = Modifier, isChecked = true) {
                navController.navigate(Screen.MonthlyMonitoringHabits.route)
            }
            TextFieldMoreLabel(
                value = cigarsConsumed,
                label = stringResource(id = R.string.label_cigars_consumed),
            ) {
                habitViewModel.onChangeHabits(
                    authData = authData,
                    cigarsConsumed = it,
                    sleepHours = hoursSleep,
                    emotionState = emotionalState,
                    perceptionOfRelaxation = perceptionRest
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            DefaultDropdownMenuCheckListMoreLabel(
                value = emotionalState,
                label = stringResource(id = R.string.label_emotional_state),
                dropItemsText = habitViewModel.listEmotionalState
            ) {
                habitViewModel.onChangeHabits(
                    authData = authData,
                    cigarsConsumed = cigarsConsumed,
                    sleepHours = hoursSleep,
                    emotionState = it,
                    perceptionOfRelaxation = perceptionRest
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(stringResource(id = R.string.label_title_drinks))
            if (habitViewModel.listDrinksConsumer.isNotEmpty() && habitViewModel.listDrinksConsumer.first() != null) {
                NonLazyVerticalGrid(
                    columns = 2,
                    data = habitViewModel.listDrinksConsumer
                ) { drink ->
                    if (drink != null)
                        ItemDrink(
                            drink = drink,
                            onUpdate = { habitViewModel.editDrink(drink) },
                            onDelete = { habitViewModel.deleteDrink(authData, drink) })
                    else AddButton(label = stringResource(id = R.string.button_add_drinks)) {
                        habitViewModel.showDialogAddDrink()
                    }
                }
            } else {
                AddButton(label = stringResource(id = R.string.button_add_drinks)) {
                    habitViewModel.showDialogAddDrink()
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            TextFieldMoreLabel(
                label = stringResource(id = R.string.label_hours_sleep),
                value = hoursSleep,
            ) {
                habitViewModel.onChangeHabits(
                    authData = authData,
                    cigarsConsumed = cigarsConsumed,
                    sleepHours = it,
                    emotionState = emotionalState,
                    perceptionOfRelaxation = perceptionRest
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            DropdownMenuMoreLabel(
                value = perceptionRest,
                label = stringResource(id = R.string.label_perception_rest),
                dropItemsText = habitViewModel.listPerceptionRest
            ) {
                habitViewModel.onChangeHabits(
                    authData = authData,
                    cigarsConsumed = cigarsConsumed,
                    sleepHours = hoursSleep,
                    emotionState = emotionalState,
                    perceptionOfRelaxation = it
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdownMenu() {
    TextFieldMoreLabel(
        value = "12",
        label = "Cigarros consumidos",
        onChanged = { }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdownMenuMoreLabel() {
    DefaultDropdownMenuCheckList(
        value = listOf("Item 1", "Item 2"),
        label = "Label",
        enabled = true,
        dropItemsText = listOf("Item 1", "Item 2", "Item 3"),
        onChanged = {  }
    )
}

@Composable
fun DropdownMenuMoreLabel(
    value: String,
    label: String,
    dropItemsText: List<String>,
    onChanged: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = Typography.bodyLarge,
            color = PrimaryColor,
            modifier = Modifier.weight(1f)
        )
        var dropItemsShow: Boolean by remember { mutableStateOf(false) }
        /*DefaultDropdownMenu(
            value = value,
            label = label,
            dropItemsText = dropItemsText,
            modifier = Modifier
        ) {
            onChanged(it)
        }*/
        Row(modifier = Modifier
            .weight(.8f)
            .border(1.dp, LabelColor, ShapeDefaults.Small)
            .clickable { dropItemsShow = true }
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value.ifEmpty { stringResource(id = R.string.label_select) },
                color = if (value.isNotEmpty()) TextColor else LabelColor,
                style = Typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier.weight(.2f)
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = dropItemsShow,
            onDismissRequest = { dropItemsShow = false }) {
            dropItemsText.forEach { text ->
                DropdownMenuItem(
                    text = {
                        Text(text)
                    }, onClick = {
                        dropItemsShow = false
                        onChanged(text)
                    })
            }
        }
    }
}

@Composable
fun TextFieldMoreLabel(value: String, label:String, onChanged: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = Typography.bodyLarge,
            color = PrimaryColor,
            modifier = Modifier.weight(1f)
        )
        BasicTextField(
            value = value,
            modifier = Modifier
                .border(1.dp, LabelColor, shape = ShapeDefaults.Small)
                .padding(16.dp)
                .weight(.8f),
            onValueChange = { onChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDrink(
    drink: DrinksModel,
    show: Boolean,
    listDrinks: List<String>,
    listCups: List<String>,
    onDismissRequest: () -> Unit,
    onChanged: (DrinksModel) -> Unit
) {
    if (show) {
        var nameDrinks: String by remember { mutableStateOf(drink.name) }
        var countDrinks: String by remember { mutableStateOf(if (drink.count >= 0) drink.count.toString() else "") }
        ModalBottomSheet(containerColor = DefaultColor , onDismissRequest = { onDismissRequest() }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onDismissRequest() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
                DropdownMenuMoreLabel(
                    value = nameDrinks,
                    label = stringResource(id = R.string.label_title_drinks),
                    dropItemsText = listDrinks
                ) {
                    nameDrinks = it
                }
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldMoreLabel(
                    value = countDrinks,
                    label = stringResource(id = R.string.label_cups),
                ) {
                    countDrinks = it
                }
                DefaultButton(label = stringResource(id = R.string.button_save_change), modifier = Modifier.fillMaxWidth()) {
                    drink.name = nameDrinks
                    drink.count = countDrinks.replace(" copas", "").toInt()
                    onChanged(
                        drink
                    )
                    onDismissRequest()
                }
            }
        }
    }
}

@Composable
fun ItemDrink(drink: DrinksModel, onUpdate: () -> Unit, onDelete: () -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, LabelColor, CircleShape)
                .padding(4.dp)
                .clickable(role = Role.Button) {
                    onUpdate()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = drink.name,
                style = Typography.titleLarge,
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${drink.count} copas",
                style = Typography.bodySmall,
                color = PrimaryColor
            )
        }
        IconButton(
            onClick = {
                onDelete()
            }, modifier = Modifier
                .padding(end = 8.dp)
                .clip(CircleShape)
                .size(24.dp)
                .background(color = PrimaryColor)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Close, tint = DefaultColor, contentDescription = "")
        }
    }
}
