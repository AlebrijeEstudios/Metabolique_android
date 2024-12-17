package com.alebrije_estudios.metabolique.dashboard.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.alebrije_estudios.metabolique.login.ui.HeaderLogo
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.ProgressMedium
import com.alebrije_estudios.metabolique.ui.theme.ProgressSlight
import com.alebrije_estudios.metabolique.ui.theme.ProgressStrong
import com.alebrije_estudios.metabolique.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true, locale = "es")
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(DashboardViewModel(), navController = rememberNavController())
}

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val data = viewModel.getData()
    val name = "Luis"
    Column {
        HeaderDashboard(name) { navController.navigate(Screen.MyProfile.route) }
        Spacer(modifier = Modifier.size(32.dp))
        LazyColumn(
            modifier = modifier.fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                HeaderLogo(modifier = Modifier)
                Spacer(modifier = Modifier.size(32.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GridItem(
                        title = stringResource(id = R.string.label_title_calories),
                        value = "${data.calories}",
                        percentageMax = data.calories.toFloat() / data.maxCalories,
                        painterID = R.drawable.ic_calories,
                        modifier = Modifier
                            .padding(end = 8.dp, bottom = 16.dp)
                            .weight(1f)
                    ) {
                        navController.navigate(Screen.Feed.route)
                    }
                    GridItem(
                        title = stringResource(id = R.string.label_title_exercise),
                        value = data.getExerciseTime(),
                        percentageMax = data.exerciseTime.toFloat() / data.maxTime,
                        painterID = R.drawable.ic_exercise,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 16.dp)
                            .weight(1f)
                        //color = Color.Red
                    ) {
                        navController.navigate(Screen.Exercises.route)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    GridItem(
                        title = stringResource(id = R.string.label_title_sleepy),
                        value = data.getSleepTime(),
                        percentageMax = data.sleepTime.toFloat() / data.maxSleepTime,
                        //color = Color.Yellow,
                        painterID = R.drawable.ic_sleepiness,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                    ) {
                        navController.navigate(Screen.Habits.route)
                    }
                    GridItem(
                        title = stringResource(id = R.string.label_title_medications),
                        value = data.getCountMedication(),
                        percentageMax = data.countMedication.toFloat() / data.maxCountMedication,
                        painterID = R.drawable.ic_meds,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                    ) {
                        navController.navigate(Screen.Medication.route)
                    }
                }
                Spacer(modifier = Modifier.size(32.dp))
            }
        }

    }
}

@Composable
fun HeaderDashboard(name: String, onClicked: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.label_greeting) + " " + name,
            style = Typography.bodyLarge,
            color = PrimaryColor,
            modifier = Modifier.padding(start = 8.dp)
        )
        TextButton(onClick = { onClicked() }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.button_profile),
                    style = Typography.bodyMedium,
                    textDecoration = TextDecoration.Underline
                )
                Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = "")
            }
        }
    }
}

@Composable
fun GridItem(
    title: String,
    value: String = "",
    percentageMax: Float,
    //color: Color = Color.Green,
    @DrawableRes painterID: Int,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    var percentage: Float by remember { mutableFloatStateOf(0f) }
    val color = if (percentage < .33f) ProgressSlight
    else if (percentage < .66f) ProgressMedium
    else ProgressStrong
    val scope = CoroutineScope(Dispatchers.IO)
    OutlinedCard(
        modifier = modifier,
        onClick = { onClicked() },
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 8.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)) {
                Text(
                    text = title,
                    modifier = Modifier.align(Alignment.Center),
                    style = Typography.bodyLarge,
                    color = PrimaryColor
                )
                Icon(
                    painter = painterResource(id = painterID),
                    tint = PrimaryColor, contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(96.dp),
                    color = color,
                    strokeWidth = 5.dp,
                    progress = {
                        scope.launch {
                            while (percentage < percentageMax) {
                                percentage += 0.01f
                                delay(1000)
                            }
                        }
                        percentage
                    },
                    trackColor = Color.LightGray
                )
                Text(text = value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}
