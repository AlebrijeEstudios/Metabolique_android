package com.alebrije_estudios.metabolique.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.exercise.ui.AddButton
import com.alebrije_estudios.metabolique.exercise.ui.TitleView
import com.alebrije_estudios.metabolique.exercise.ui.translate
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.NonLazyVerticalGrid
import com.alebrije_estudios.metabolique.ui.theme.BackgroundColor
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.RedColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Preview(locale = "es")
@Composable
fun FeedScreenPreview() {
    val viewmodel = FeedViewModel()
    FeedScreen(viewmodel, rememberNavController())
}

@Composable
fun FeedScreen(viewModel: FeedViewModel, navController: NavController) {
    val date: LocalDate by viewModel.date.observeAsState(initial = LocalDate.now())
    val listFoods = viewModel.getListFoods()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TitleView(text = stringResource(id = R.string.title_food))
        item {
            DateSelected(date) {
                viewModel.onDateChanged(it)
            }
            Spacer(modifier = Modifier.size(16.dp))
            GraphicBars(
                stringResource(id = R.string.label_title_calories),
                mapOf(
                    date.minusDays(6).getDayMonthYear() to 0,
                    date.minusDays(5).getDayMonthYear() to 0,
                    date.minusDays(4).getDayMonthYear() to 0,
                    date.minusDays(3).getDayMonthYear() to 0,
                    date.minusDays(2).getDayMonthYear() to 0,
                    date.minusDays(1).getDayMonthYear() to 0,
                    date.getDayMonthYear() to 0)
            )
            MonthlyFollowUp(isChecked = false) {}
            Spacer(modifier = Modifier.size(16.dp))
            //val stateGrid = rememberLazyGridState()
            /*LazyVerticalGrid(
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = true,
                columns = GridCells.Fixed(2)
            ) {
                items(listFoods) { food ->
                    FoodItem(food, navController = navController)
                }
                item {

                }
            }*/
            NonLazyVerticalGrid(columns = 2, data = listFoods) { item ->
                if(item != null)
                    FoodItem(food = item, navController = navController)
                else
                    AddButton(label = stringResource(id = R.string.label_add_food)) {

                    }
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun GraphicBars(title: String, data: Map<String,Number>) {
    if (data.isNotEmpty()) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = PrimaryColor,
                    style = Typography.titleLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                val modelProducer = remember { CartesianChartModelProducer() }
                val labelListKey = ExtraStore.Key<List<String>>()
                val columnCartesianLayer = rememberColumnCartesianLayer(
                    //dataLabel = TextComponent()
                )


                LaunchedEffect(Unit) { modelProducer.runTransaction {
                    columnSeries { series(
                        y=data.values
                    ) }
                    extras {
                        it[labelListKey] = data.keys.toList()
                    }
                    CartesianValueFormatter { x, chartValues, _ -> chartValues.model.extraStore[labelListKey][x.toInt()] }
                }
                }

                CartesianChartHost(
                    rememberCartesianChart(
                        columnCartesianLayer,
                        startAxis = rememberStartAxis(),
                        bottomAxis = rememberBottomAxis(
                            titleComponent = TextComponent()
                        ),
                        decorations = listOf(rememberHorizontalLine(y = {10.0}, LineComponent(1,0.5f,)))
//                    marker = rememberDefaultCartesianMarker(label =  )
                    ),
                    modelProducer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun MonthlyFollowUp(modifier: Modifier = Modifier, isChecked: Boolean, onClicked: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isChecked) PrimaryColor else RedColor,
            contentColor = DefaultColor,
            disabledContainerColor = LabelColor,
            disabledContentColor = DefaultColor
        ),
        onClick = { }) {
        Row(
            modifier = modifier.clickable { onClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_monthly_follow_up),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
            )
            Spacer(modifier = Modifier.size(8.dp))
            if (isChecked) Icon(
                painter = painterResource(id = R.drawable.ic_info_tracking),
                contentDescription = ""
            )
            else Icon(
                painter = painterResource(id = R.drawable.ic_empty_tracking),
                contentDescription = ""
            )
        }
    }
}


@Composable
fun FoodItem(food: FoodModel, navController: NavController) {
    Box {
        OutlinedCard(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .fillMaxWidth(),
            onClick = { navController.navigate(Screen.CapturedFood.route) },
            shape = CircleShape
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(60.dp)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Icon(
                    imageVector = if (food.isCheck) Icons.Filled.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    tint = if (food.isCheck) BackgroundColor else Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(24.dp),
                    contentDescription = ""
                )
                Column {
                    Text(
                        text = food.name,
                        color = TextColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (food.calories > 0) {
                        //Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "${food.calories} cal",
                            color = TextColor,

                            )
                    }
                }
            }
        }
        if (food.isCheck) {
            IconButton(
                onClick = {}, modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(CircleShape)
                    .size(24.dp)
                    .background(color = PrimaryColor)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = DefaultColor,
                    contentDescription = ""
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelected(date: LocalDate, onChangeDate: (LocalDate) -> Unit) {
    var showDialogPicker: Boolean by remember { mutableStateOf(false) }
    val stateDate = rememberDatePickerState()
    Button(
        onClick = { showDialogPicker = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = DefaultColor,
            disabledContainerColor = LabelColor,
            disabledContentColor = DefaultColor
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = date.getDayMonthYear())
            Spacer(modifier = Modifier.size(8.dp))
            Icon(painter = painterResource(id = R.drawable.ic_edit_date), contentDescription = "")
        }
    }
    if (showDialogPicker) {
        DatePickerDialog(onDismissRequest = { showDialogPicker = false }, confirmButton = {
            Button(onClick = {
                showDialogPicker = false
                stateDate.selectedDateMillis?.let {
                    onChangeDate(
                        Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                    )
                }
            }) {
                Text(text = stringResource(id = R.string.label_confirm))
            }
        }) {
            DatePicker(state = stateDate)
        }
    }
    //val localDate = Instant.ofEpochMilli(date).atZone(ZoneId.of("UTC")).toLocalDate()
    /*Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            onChangeDate(date - 86400000)
        }) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = value, modifier = Modifier.clickable { showDialogPicker = true })
        Spacer(modifier = Modifier.size(8.dp))
        IconButton(onClick = {
            onChangeDate(date + 86400000)
        }) {
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
        }
        if (showDialogPicker) {
            DatePickerDialog(onDismissRequest = { showDialogPicker = false }, confirmButton = {
                Button(onClick = {
                    showDialogPicker = false
                    stateDate.selectedDateMillis?.let {
                        onChangeDate(it)
                    }
                }) {
                    Text(text = stringResource(id = R.string.label_confirm))
                }
            }) {
                DatePicker(state = stateDate)
            }
        }
    }*/
}

fun LocalDate.getDayMonthValueYear(): String = "${this.dayOfMonth.toString().padStart(2, '0')}/${
    this.monthValue.toString().padStart(2, '0')
}/${this.year}"

fun LocalDate.getDayMonthYear(): String = "${this.dayOfMonth.toString().padStart(2, '0')} / ${
    this.month.translate().substring(0..2)
} / ${this.year}"


