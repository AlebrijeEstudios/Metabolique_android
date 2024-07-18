package com.alebrije_estudios.metabolique.composeble

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun DefaultTextField(value:String,onValueChanged:(String) -> Unit){
    TextField(value = value, onValueChange = {onValueChanged(it)})
}