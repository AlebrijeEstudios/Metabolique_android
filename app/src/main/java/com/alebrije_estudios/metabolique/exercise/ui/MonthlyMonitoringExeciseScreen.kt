package com.alebrije_estudios.metabolique.exercise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalDate
import java.time.Month

@Preview(locale = "es", showBackground = true)
@Composable
fun MonthlyMonitoringExerciseScreenPreview() {
    MonthlyMonitoringExerciseScreen(LocalDate.now(), Modifier)
}

@Composable
fun MonthlyMonitoringExerciseScreen(month: LocalDate, modifier: Modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        BoxMonth(month.month.translate() + " del ${month.year}", modifier.align(Alignment.CenterHorizontally))
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question1_exercise),
            listOption = listOf(),
            isApply = false,
            onChangeApply = {}) {}
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question2_exercise),
            listOption = listOf(),
            isApply = false,
            onChangeApply = {}) {}
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question3_exercise),
            listOption = listOf(),
            isApply = false, {}) {}
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question4_exercise),
            listOption = listOf(),
            isApply = false,
            onChangeApply = {}) {}
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question5_exercise),
            listOption = listOf(),
            isApply = false,
            onChangeApply = {}) {}
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question6_exercise),
            listOption = listOf(),
            isApply = false,
            onChangeApply = {}) {}
        QuestionSelection(
            answer = "",
            question = stringResource(id = R.string.text_question7_exercise),
            listOption = listOf(),
            onChangeApply = {}) {}
        ButtonSubmit() {}
    }
}

@Composable
fun ButtonSubmit(onClick: () -> Unit) {
    DefaultButton(label = stringResource(id = R.string.button_submit)) {
        onClick()
    }
}

@Composable
fun QuestionSelection(
    answer: String,
    question: String,
    listOption: List<String>,
    isApply: Boolean? = null,
    onChangeApply: (Boolean) -> Unit,
    answerChange: (String) -> Unit
) {
    Text(
        text = question,
        style = Typography.titleMedium,
        color = TextColor,
        modifier = Modifier.padding(top = 16.dp)
    )
    DefaultDropdownMenu(
        value = answer,
        enabled = isApply == null || !isApply,
        label = stringResource(id = R.string.label_answer),
        dropItemsText = listOption
    ) {
        answerChange(it)
    }
    if (isApply != null) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isApply, onCheckedChange = { onChangeApply(!isApply) })
            Text(
                text = stringResource(id = R.string.label_apply),
                style = Typography.bodyMedium,
                color = TextColor
            )
        }
    }

}

fun Month.translate(): String {
    return when (this) {
        Month.JANUARY -> "Enero"
        Month.FEBRUARY -> "Febrero"
        Month.MARCH -> "Marzo"
        Month.APRIL -> "Abril"
        Month.MAY -> "Mayp"
        Month.JUNE -> "Junio"
        Month.JULY -> "Julio"
        Month.AUGUST -> "Agosto"
        Month.SEPTEMBER -> "Septiembre"
        Month.OCTOBER -> "Octubre"
        Month.NOVEMBER -> "Noviembre"
        Month.DECEMBER -> "Diciembre"
        else -> "" // or any other default value you want to use in case the month is not recognized. For example, you can return "Unknown" or an empty string.  // or any other default value you want to use in case the month is not recognized. For example, you can return "Unknown" or an empty string
    }
}

@Composable
fun BoxMonth(month: String, modifier: Modifier  = Modifier){
    Box(
        modifier
            .clip(CircleShape)
            .background(PrimaryColor)
            .padding(16.dp)
    ) {
        Text(
            text = month,
            style = Typography.titleLarge,
            color = DefaultColor,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
