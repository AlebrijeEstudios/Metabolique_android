package com.alebrije_estudios.metabolique.medication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.exercise.ui.BoxMonth
import com.alebrije_estudios.metabolique.exercise.ui.translate
import com.alebrije_estudios.metabolique.my_profile.ui.ButtonSave
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import java.time.LocalDate

@Preview
@Composable
fun PreviewMonthlyMonitoringMedicationScreen(){
    MonthlyMonitoringMedicationScreen(LocalDate.now())
}

@Composable
fun MonthlyMonitoringMedicationScreen(month: LocalDate) {
    val answers = remember { mutableStateListOf<Boolean?>(null,null,null,null)  }
    var count by remember { mutableIntStateOf(0) }
    var isEnabled by remember { mutableStateOf( false)}
    isEnabled = count >= 4
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxMonth(month=month.month.translate()+ " del ${month.year}")
        QuestionYesOrNot(answer = answers[0], question = stringResource(id = R.string.text_question1_medication)) {
            if(answers[0] == null)count++
            answers[0] = it
        }
        QuestionYesOrNot(answer = answers[1], question = stringResource(id = R.string.text_question2_medication)) {
            if(answers[1] == null)count++
            answers[1] = it
        }
        QuestionYesOrNot(answer = answers[2], question = stringResource(id = R.string.text_question3_medication)) {
            if(answers[2] == null)count++
            answers[2] = it
        }
        QuestionYesOrNot(answer = answers[3], question = stringResource(id = R.string.text_question4_medication)) {
            if(answers[3] == null)count++
            answers[3] = it
        }
        ButtonSave(isEnabled = isEnabled) {

        }
    }

}

@Composable
fun QuestionYesOrNot(answer: Boolean?, question: String, onChangeApply: (Boolean) -> Unit) {
    Text(text = question)
    OutlinedButton(onClick = {onChangeApply(true)}, shape = ShapeDefaults.Small, modifier = Modifier.fillMaxWidth(), enabled = if (answer == null) true else !answer, colors = ButtonDefaults.outlinedButtonColors(
        containerColor =  DefaultColor,
        disabledContainerColor = PrimaryColor,
        contentColor = PrimaryColor,
        disabledContentColor = DefaultColor
    )){
        Text(text = stringResource(id = R.string.button_yes))
    }
    OutlinedButton(onClick = { onChangeApply(false)}, shape = ShapeDefaults.Small, modifier = Modifier.fillMaxWidth(), enabled = answer ?: true, colors = ButtonDefaults.outlinedButtonColors(
        containerColor =  DefaultColor,
        disabledContainerColor = PrimaryColor,
        contentColor = PrimaryColor,
        disabledContentColor = DefaultColor
    )){
        Text(text = stringResource(id = R.string.button_no))
    }
}