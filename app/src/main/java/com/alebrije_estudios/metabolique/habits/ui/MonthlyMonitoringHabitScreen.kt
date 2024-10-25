package com.alebrije_estudios.metabolique.habits.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.exercise.ui.BoxMonth
import com.alebrije_estudios.metabolique.exercise.ui.QuestionSelection
import com.alebrije_estudios.metabolique.exercise.ui.translate
import com.alebrije_estudios.metabolique.my_profile.ui.ButtonSave
import com.alebrije_estudios.metabolique.ui.DefaultTextField
import com.alebrije_estudios.metabolique.ui.DefaultTimePicker
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalDate
import java.time.LocalTime

@Preview(locale = "es", showBackground = true)
@Composable
fun MonthlyMonitoringHabitsScreenPreview() {
    MonthlyMonitoringHabitScreen(LocalDate.now(), MonthlyMonitoringHabitViewModel())
}

@Composable
fun MonthlyMonitoringHabitScreen(
    date: LocalDate,
    monthlyMonitoringHabitViewModel: MonthlyMonitoringHabitViewModel
) {
    val answer1: LocalTime? by monthlyMonitoringHabitViewModel.answer1.observeAsState(null)
    val answer2: String by monthlyMonitoringHabitViewModel.answer2.observeAsState("")
    val answer3: LocalTime? by monthlyMonitoringHabitViewModel.answer3.observeAsState(null)
    val answer4: String by monthlyMonitoringHabitViewModel.answer4.observeAsState("")
    val answer6: Int by monthlyMonitoringHabitViewModel.answer6.observeAsState(-1)
    val answer7: Int by monthlyMonitoringHabitViewModel.answer7.observeAsState(-1)
    val answer8: Int by monthlyMonitoringHabitViewModel.answer8.observeAsState(-1)
    val answer9: Int by monthlyMonitoringHabitViewModel.answer9.observeAsState(-1)
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        BoxMonth(
            month = date.month.translate() + " del ${date.year}",
            Modifier.align(Alignment.CenterHorizontally)
        )
        QuestionTime(
            answer = answer1,
            question = stringResource(id = R.string.text_question1_habits),
           ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= it,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4,
               // answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = answer7,
                answer8 = answer8,
                answer9 = answer9
                )
        }
        QuestionOpen(
            answer = answer2,
            keyboardType = KeyboardType.NumberPassword,
            question = stringResource(id = R.string.text_question2_habits),
            ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = it,
                answer3 = answer3,
                answer4 = answer4,
               // answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = answer7,
                answer8 = answer8,
                answer9 = answer9
            )
        }
        QuestionTime(
            answer = answer3,
            question = stringResource(id = R.string.text_question3_habits),
           ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = answer2,
                answer3 = it,
                answer4 = answer4,
               // answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = answer7,
                answer8 = answer8,
                answer9 = answer9
            )
        }
        QuestionOpen(
            answer = answer4,
            keyboardType = KeyboardType.NumberPassword,
            question = stringResource(id = R.string.text_question4_habits),
           ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = it,
               // answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = answer7,
                answer8 = answer8,
                answer9 = answer9
            )
        }
        Text(
            text = stringResource(id = R.string.title_question5_habits),
            style = Typography.titleMedium,
            color = TextColor,
            modifier = Modifier.padding(top = 16.dp)
        )
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[0],
            question = stringResource(id = R.string.text_question5a_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,0)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[1],
            question = stringResource(id = R.string.text_question5b_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,1)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[2],
            question = stringResource(id = R.string.text_question5c_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,2)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[3],
            question = stringResource(id = R.string.text_question5d_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,3)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[4],
            question = stringResource(id = R.string.text_question5e_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,4)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[5],
            question = stringResource(id = R.string.text_question5f_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,5)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[6],
            question = stringResource(id = R.string.text_question5g_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,6)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[7],
            question = stringResource(id = R.string.text_question5h_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,7)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[8],
            question = stringResource(id = R.string.text_question5i_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,8)
        }
        QuestionSelectionButton(
            answer = monthlyMonitoringHabitViewModel.answer5[9],
            question = stringResource(id = R.string.text_question5j_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers5(it,9)
        }
        QuestionSelectionButton(
            answer = answer6,
            question = stringResource(id = R.string.text_question6_habits),
            listOption = monthlyMonitoringHabitViewModel.optionsValuationOptions()) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4,
                //answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = it,
                answer7 = answer7,
                answer8 = answer8,
                answer9 = answer9
            )
        }
        QuestionSelectionButton(
            answer = answer7,
            question = stringResource(id = R.string.text_question7_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
            ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4,
                //answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = it,
                answer8 = answer8,
                answer9 = answer9
            )
        }
        QuestionSelectionButton(
            answer = answer8,
            question = stringResource(id = R.string.text_question8_habits),
            listOption = monthlyMonitoringHabitViewModel.optionFrequencyWeekly(),
            ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4,
                //answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = answer7,
                answer8 = it,
                answer9 = answer9
            )
        }
        QuestionSelectionButton(
            answer = answer9,
            question = stringResource(id = R.string.text_question9_habits),
            listOption = monthlyMonitoringHabitViewModel.optionsDifficulty()
        ) {
            monthlyMonitoringHabitViewModel.changedAnswers(
                answer1= answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4,
               // answer5 = monthlyMonitoringHabitViewModel.answer5,
                answer6 = answer6,
                answer7 = answer7,
                answer8 = answer8,
                answer9 = it
            )
        }
        ButtonSave(isEnabled = true) {
            
        }
    }

}

@Composable
fun QuestionTime(answer: LocalTime?, question: String, onChange: (LocalTime) -> Unit) {
    Text(
        text = question,
        style = Typography.titleMedium,
        color = TextColor,
        modifier = Modifier.padding(top = 16.dp)
    )
   DefaultTimePicker(value = answer, label = stringResource(id = R.string.label_answer), modifier=  Modifier.fillMaxWidth()) {
       onChange(it)
   }
}

@Composable
fun QuestionOpen(answer: String, question: String, keyboardType: KeyboardType = KeyboardType.Text, onChange:(String) -> Unit){
    Text(
        text = question,
        style = Typography.titleMedium,
        color = TextColor,
        modifier = Modifier.padding(top = 16.dp)
    )
    DefaultTextField(value = answer, keyboardType = keyboardType, label = stringResource(id = R.string.label_answer), modifier = Modifier.fillMaxWidth()) {
        onChange(it)
    }
}


@Composable
fun QuestionSelectionButton(
    answer: Int?,
    question: String,
    listOption: List<String>,
    onChanged: (Int) -> Unit
) {
    Text(
        text = question,
        style = Typography.titleMedium,
        color = TextColor,
        modifier = Modifier.padding(top = 16.dp)
    )
    listOption.forEachIndexed { index, option ->
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedButton(
            onClick = { onChanged(index) },
            shape = ShapeDefaults.Small,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, PrimaryColor),
            enabled = answer != index,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = DefaultColor,
                disabledContainerColor = PrimaryColor,
                contentColor = PrimaryColor,
                disabledContentColor = DefaultColor,
            )
        ) {
            Text(text = option, style = Typography.bodyLarge)
        }
    }
}
