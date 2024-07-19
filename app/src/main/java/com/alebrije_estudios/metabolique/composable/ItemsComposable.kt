package com.alebrije_estudios.metabolique.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.alebrije_estudios.metabolique.ui.theme.Black
import com.alebrije_estudios.metabolique.ui.theme.Gray
import com.alebrije_estudios.metabolique.ui.theme.LightGray
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.Red

@Composable
fun DefaultTextField(
    value:String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIcon:@Composable () -> Unit = {},
    label:String,
    imeAction:ImeAction = ImeAction.Next,
    isError:Boolean = false,
    suffix: @Composable () -> Unit = {},
    maxLines:Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChanged:(String) -> Unit){
    OutlinedTextField(
        value = value,
        onValueChange = {onValueChanged(it)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryColor,
            unfocusedBorderColor = Gray,
            errorBorderColor = Red,
            focusedTextColor = Black,
            focusedLabelColor = Gray,
            unfocusedTextColor = Black
        ),
        enabled = enabled,
        label ={
            Text(text = label)
        },
        suffix = {
            suffix()
                 },
        isError = isError,
        trailingIcon = trailingIcon,
        singleLine = maxLines == 1,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        maxLines = maxLines,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun DefaultDropdownMenu(
    value: String,
    label: String,
    suffix: @Composable () -> Unit = {},
    dropItemsText: List<String>,
    onChanged: (String) -> Unit) {
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }
    Column {
        DefaultTextField(
            value = value,
            suffix = {suffix()},
            modifier = Modifier.clickable {
                expanded = !expanded
            },
            enabled = false,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            },
            label = label
        ) {
            onChanged(it)
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            dropItemsText.forEach { text ->
                DropdownMenuItem(
                    text = {
                    Text(text)
                }, onClick = {
                    expanded = false
                    onChanged(text)
                })
            }
        }
    }

}

@Composable
fun DefaultButton(
    label: String,
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClicked:()-> Unit
){
  Button(
      onClick = onClicked,
      enabled = enabled,
      modifier = modifier.fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(
          containerColor = PrimaryColor,
          contentColor = Black,
          disabledContainerColor = LightGray,
          disabledContentColor = Gray
      )
  ) {
      Text(text = label)
  }
}