package com.alebrije_estudios.metabolique.exercise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.feed.ui.DateSelected
import com.alebrije_estudios.metabolique.feed.ui.GraphicBars
import com.alebrije_estudios.metabolique.feed.ui.MonthlyFollowUp
import com.alebrije_estudios.metabolique.feed.ui.getDayMonthValueYear
import com.alebrije_estudios.metabolique.feed.ui.getDayMonthYear
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.my_profile.ui.ButtonSave
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.NonLazyVerticalGrid
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.Lexend
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.RedColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalDate


@Preview
@Composable
fun DialogPreview() {
    DeleteDialog(
        message = stringResource(id = R.string.text_message_delete_exercise),
        show = true,
        onDismissRequest = { },
        onDelete = {},
    )
}

//
//@Preview(showBackground = true, locale = "es")
//@Composable
//fun ExerciseScreenPreview() {
//    ExerciseScreen(ExerciseViewModel(), rememberNavController())
//}

@Composable
fun ExerciseScreen(viewModel: ExerciseViewModel, authData: AuthData, navController: NavController) {
    //viewModel.getListOfExercises(authData)
    val showDialogAddActivity by viewModel.showDialogAddActivity.observeAsState(initial = false)
    val localDate by viewModel.localDate.observeAsState(initial = LocalDate.now())
    val exercise by viewModel.exercise.observeAsState(initial = ExercisesModel("", "", "", -1))
    val showDialogAction by viewModel.showDialogAction.observeAsState(initial = false)
    if (showDialogAddActivity)
        AddActivityDialog(
            exercise,
            viewModel.listTypes,
            viewModel.listIntensities,
            viewModel.listTimes,
            onDismissRequest = { viewModel.hiddenDialog() },
            { viewModel.onChange(it) }) {
            viewModel.savesChanges(authData, it)
        }
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .scrollable(rememberScrollState(), Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            DateSelected(localDate) {
                viewModel.changeDate(it, authData)
            }
            GraphicBars(
                stringResource(id = R.string.label_title_exercise),
                mapOf(
                    localDate.minusDays(6).getDayMonthValueYear() to 10,
                    localDate.minusDays(5).getDayMonthValueYear() to 0,
                    localDate.minusDays(4).getDayMonthValueYear() to 5,
                    localDate.minusDays(3).getDayMonthValueYear() to 15,
                    localDate.minusDays(2).getDayMonthValueYear() to 0,
                    localDate.minusDays(1).getDayMonthValueYear() to 0,
                    "A" to 0)
            )
            MonthlyFollowUp(isChecked = true) {
                navController.navigate(Screen.MonthlyMonitoringExercises.route)
            }
            if (viewModel.listExercises.isNotEmpty() && viewModel.listExercises.first() != null)
                ListViewExercises(
                    listExercises = viewModel.listExercises,
                    onAdd = {viewModel.showDialog()},
                    onDelete = { exercise -> viewModel.showDialogAction(exercise) },
                    onUpdate = { exercise -> viewModel.editExercise(exercise) }
                )
            else
                EmptyListViewExercises{viewModel.showDialog()}
            DeleteDialog(
                message = stringResource(id = R.string.text_message_delete_exercise),
                show = showDialogAction,
                onDismissRequest = { viewModel.hideDialogAction() },
                onDelete = { viewModel.deleteExercise(authData, exercise) })
        }
    }
}

//@Preview(locale = "es")
@Composable
fun EmptyListViewExercises(onAdd: () -> Unit) {
    Spacer(modifier = Modifier.size(32.dp))
    Text(
        text = stringResource(id = R.string.text_message_no_exersice),
        style = Typography.bodyLarge,
        color = LabelColor
    )
    Spacer(modifier = Modifier.size(8.dp))
    AddActivity {
        onAdd()
    }
}

@Composable
fun ListViewExercises(
    listExercises: List<ExercisesModel?>,
    onAdd: () -> Unit,
    onDelete: (ExercisesModel) -> Unit,
    onUpdate: (ExercisesModel) -> Unit
) {
     NonLazyVerticalGrid(columns = 2 , data = listExercises ) { exercise ->
         if(exercise != null)
            ExerciseItem(exercise, onDelete = {onDelete(exercise)}, onUpdate = {onUpdate(exercise)})
         else
             AddActivity {onAdd()}
     }
}

@Composable
fun TitleView(text: String) {
    Spacer(modifier = Modifier.size(12.dp))
    Text(
        text = text,
        fontSize = 22.sp,
        fontFamily = Lexend,
        fontWeight = FontWeight.ExtraBold,
        color = TextColor
    )
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun DeleteDialog(
    message: String,
    show: Boolean = false,
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            containerColor = DefaultColor,
            confirmButton = {
                CancelButton {
                    onDismissRequest()
                }
                DeleteButton {
                    onDelete()
                    onDismissRequest()
                }
            },
            text = {
                Column {
                    Spacer(Modifier.size(32.dp))
                    Text(
                        text = message,
                        style = Typography.bodyLarge,
                        color = PrimaryColor
                    )
                    Spacer(Modifier.size(32.dp))
                }
            }
        )

    }
}

@Composable
fun CancelButton(onClicked: () -> Unit) {
    OutlinedButton(
        onClick = { onClicked() },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = DefaultColor,
            contentColor = TextColor
        )
    ) {
        Text(text = stringResource(id = R.string.button_cancel))
    }
}

@Composable
fun DeleteButton(onClicked: () -> Unit) {
    Button(
        onClick = { onClicked() },
        colors = ButtonDefaults.buttonColors(
            contentColor = DefaultColor,
            containerColor = RedColor,
        )
    ) {
        Text(stringResource(id = R.string.button_delete))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityDialog(
    exercise: ExercisesModel,
    listTypes: List<String>,
    listIntensities: List<String>,
    listTimes: List<String>,
    onDismissRequest: () -> Unit,
    onChange: (ExercisesModel) -> Unit,
    onSaves: (ExercisesModel) -> Unit
) {
    var type: String by remember { mutableStateOf(exercise.type) }
    var intensity: String by remember { mutableStateOf(exercise.intensity) }
    var time: String by remember { mutableStateOf(if (exercise.time >= 0) exercise.time.toString() + " min" else "") }
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            IconButton(modifier = Modifier.align(Alignment.End), onClick = { onDismissRequest() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = ""
                )
            }
            DefaultDropdownMenu(
                value = type,
                label = stringResource(id = R.string.label_type),
                dropItemsText = listTypes
            ) {
                type = it
                onChange(exercise)
            }
            Spacer(modifier = Modifier.size(8.dp))
            DefaultDropdownMenu(
                value = intensity,
                label = stringResource(id = R.string.label_intensity),
                dropItemsText = listIntensities
            ) {
                intensity = it
                onChange(exercise)
            }
            Spacer(modifier = Modifier.size(8.dp))
            DefaultDropdownMenu(
                value = time,
                label = stringResource(id = R.string.label_time),
                dropItemsText = listTimes
            ) {
                time = it
                onChange(exercise)
            }
            Spacer(modifier = Modifier.size(16.dp))
            ButtonSave(isEnabled = true) {
                exercise.type = type
                exercise.intensity = intensity
                exercise.time = time.replace(" min", "").toInt()
                time = ""
                intensity = ""
                type = ""
                onSaves(exercise)
            }
        }
    }
}

@Composable
fun AddActivity(onClicked: () -> Unit) {
    AddButton(
        label = stringResource(id = R.string.button_add_activity),
        modifier = Modifier.padding(top = 6.dp)
    ) {
        onClicked()
    }
}

@Composable
fun AddButton(label: String, modifier: Modifier = Modifier, onClicked: () -> Unit) {
    Button(
        onClick = { onClicked() },
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = DefaultColor
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = label, style = Typography.bodyLarge, textAlign = TextAlign.Center)
        }

    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun ExercisesListItem() {
    ListViewExercises(
        listExercises = listOf(
            ExercisesModel("Ejercicio 1", "Sentadilla", "Muy fuerte", 120),
            ExercisesModel("Ejercicio 2", "Push-ups", "Fuerte", 60),
            ExercisesModel("Ejercicio 3", "Plancha", "Moderada", 90)
        ),
        {},
        {},
        {}
    )
}

@Composable
fun ExerciseItem(exercise: ExercisesModel, onUpdate: () -> Unit, onDelete: () -> Unit) {
    Box {
        Column(
            Modifier
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
                text = exercise.type,
                style = Typography.titleLarge,
                color = PrimaryColor
            )
            Text(
                "${exercise.time} mins",
                textAlign = TextAlign.End,
                style = Typography.bodySmall
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
