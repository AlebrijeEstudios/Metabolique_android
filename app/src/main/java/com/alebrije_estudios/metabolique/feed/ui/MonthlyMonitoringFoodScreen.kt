package com.alebrije_estudios.metabolique.feed.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alebrije_estudios.metabolique.exercise.ui.BoxMonth
import com.alebrije_estudios.metabolique.exercise.ui.translate
import java.time.LocalDate

@Preview(locale = "ES", showBackground = true)
@Composable
fun PreviewMonthlyMonitoringFoodScreen(){
    MonthlyMonitoringFoodScreen(LocalDate.now())
}

@Composable
fun MonthlyMonitoringFoodScreen(month: LocalDate) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
       BoxMonth(month = month.month.translate() + "del ${month.year}", Modifier.align(Alignment.CenterHorizontally))
        
    }
}
