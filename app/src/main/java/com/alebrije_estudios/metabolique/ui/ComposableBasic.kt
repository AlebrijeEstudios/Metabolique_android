package com.alebrije_estudios.metabolique.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.food_capture.ui.TimePickerDialog
import com.alebrije_estudios.metabolique.food_capture.ui.toFormat12
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.Lexend
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.RedColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalTime


@Composable
fun DefaultTextField(
    value: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    trailingIcon: @Composable () -> Unit = {},
    label: String,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PrimaryColor,
        unfocusedBorderColor = LabelColor,
        errorBorderColor = RedColor,
        focusedTextColor = TextColor,
        focusedLabelColor = LabelColor,
        unfocusedTextColor = TextColor,
    ),
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    suffix: @Composable () -> Unit = {},
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        textStyle = TextStyle(
            fontSize = 15.sp,
            fontFamily = Lexend
        ),
        colors = colors,
        enabled = enabled,
        label = {
            Text(
                text = label,
                color =LabelColor,
                fontSize = 11.sp,
                fontFamily = Lexend
            )
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
    )
}

@Composable
fun DefaultDropdownMenu(
    value: String,
    label: String,
    enabled: Boolean = true,
    suffix: @Composable () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth(),
    dropItemsText: List<String>,
    onChanged: (String) -> Unit
) {
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }
    Column {
        DefaultTextField(
            value = value,
            suffix = { suffix() },
            modifier = modifier.clickable(enabled = enabled) {
                expanded = !expanded
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = RedColor,
                disabledTextColor = TextColor,
                disabledLabelColor = LabelColor,
                disabledTrailingIconColor = LabelColor,
                disabledSuffixColor = TextColor
            ),
            enabled = false,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            },
            label = label
        ) {
            onChanged(it)
        }
        DropdownMenu(
            modifier = modifier,
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
fun DefaultDropdownMenuCheckList(
    value: List<String>,
    label: String,
    enabled: Boolean = true,
    suffix: @Composable () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth(),
    dropItemsText: List<String>,
    onChanged: (List<String>) -> Unit
) {
    val checkList = mutableListOf<String>()
    checkList.addAll(value)
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }
    var newValue = ""
    value.forEach {
        newValue += "$it, "  
    }
    newValue = newValue.dropLast(2)
    Column {
        DefaultTextField(
            value = newValue,
            suffix = { suffix() },
            modifier = modifier.clickable(enabled = enabled) {
                expanded = !expanded
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = RedColor,
                disabledTextColor = TextColor,
                disabledLabelColor = LabelColor,
                disabledTrailingIconColor = LabelColor,
                disabledSuffixColor = TextColor
            ),
            enabled = false,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            },
            label = label
        ) {
            //onChanged(it)
        }
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            dropItemsText.forEach { text ->
                DropdownMenuItem(
                    text = {
                        Row{
                            if(checkList.contains(text)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_info_tracking),
                                    contentDescription = "",
                                    tint = Color.Green
                                )
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(text)
                        }
                    }, onClick = {
                        //expanded = false
                        if(checkList.contains(text)) {
                            checkList.remove(text)
                        } else {
                            checkList.add(text)
                        }
                        onChanged(checkList)
                    })
            }
        }
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDropdownMenuCheckListMoreLabel(){
    DefaultDropdownMenuCheckListMoreLabel(
        value = listOf("Item 1", "Item 2"),
        label = "Label",
        enabled = true,
        dropItemsText = listOf("Item 1", "Item 2", "Item 3"),
        onChanged = {  }
    )
}

@Composable
fun DefaultDropdownMenuCheckListMoreLabel(
    value: List<String>,
    label: String,
    enabled: Boolean = true,
    suffix: @Composable () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth(),
    dropItemsText: List<String>,
    onChanged: (List<String>) -> Unit
) {
    val checkList = mutableListOf<String>()
    checkList.addAll(value)
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }
    var newValue = ""
    value.forEach {
        newValue += "$it, "
    }
    newValue = newValue.dropLast(2)
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                style = Typography.bodyLarge,
                color = PrimaryColor,
                modifier = Modifier.weight(1f)
            )
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
                .clickable { expanded = true }
                .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = newValue.ifEmpty { stringResource(id = R.string.label_select) },
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
        }
    }
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onDismissRequest = { expanded = false }) {
        dropItemsText.forEach { text ->
            DropdownMenuItem(
                text = {
                    Row{
                        if(checkList.contains(text)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info_tracking),
                                contentDescription = "",
                                tint = Color.Green
                            )
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(text)
                    }
                }, onClick = {
                    //expanded = false
                    if(checkList.contains(text)) {
                        checkList.remove(text)
                    } else {
                        checkList.add(text)
                    }
                    onChanged(checkList)
                })
        }
    }

}

@Composable
fun DefaultTimePicker(value:LocalTime?, label: String,modifier: Modifier = Modifier, onChanged:(LocalTime) -> Unit){
    var showTimePicker:Boolean by remember { mutableStateOf(false) }
    DefaultTextField(
        value = value?.toFormat12()?:"",
        label =label,
        modifier = modifier.clickable { showTimePicker = true },
        colors = OutlinedTextFieldDefaults.colors(
            disabledLabelColor = LabelColor,
            disabledTextColor = TextColor
        ),
        enabled = false,
        trailingIcon = { Icon(imageVector = Icons.Default.Alarm, contentDescription ="")}
        ) {
    }
    TimePickerDialog(showDialog = showTimePicker ,onDismissRequest = { showTimePicker = false }) {
        onChanged(it)
        showTimePicker = false
    }
}

@Composable
fun DefaultButton(
    label: String,
    enabled: Boolean = true,
    containerColor: Color = PrimaryColor,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = DefaultColor,
            disabledContainerColor = LabelColor,
            disabledContentColor = DefaultColor
        )
    ) {
        Text(text = label,
            fontSize = 15.sp,
            fontFamily = Lexend
        )
    }
}

@Composable
fun <T> NonLazyVerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int,
    data: List<T>,
    verticalSpacing: Dp = 0.dp,
    horizontalSpacing: Dp = 0.dp,
    itemContent: @Composable (item: T?) -> Unit
) {

    Box(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val numOfRows = (data.size / columns) + (if (data.size % columns > 0) 1 else 0)

            repeat(numOfRows ) { i ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
                ) {

                    repeat(columns) { j ->

                        val index = j + (i * columns)

                        if (index < data.size) {
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(index <= data.size)
                                    itemContent(data[index])
                                else
                                    itemContent(null)
                            }
                        } else {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(verticalSpacing))

            }
        }

    }

}

@Preview
@Composable
fun PreviewDialog(){
    DefaultDialog(show = true, title = "Title", message = "This is a message", onDismissRequest = {})
}


@Composable
fun DefaultDialog(show: Boolean, title: String, message: String, onDismissRequest:() -> Unit) {
    if (show)
        AlertDialog(
            onDismissRequest = { onDismissRequest()},
            confirmButton = { DefaultButton(label = "Ok",onClicked = {onDismissRequest() })},
            //title = { Text(text = title) },
            text = { Text(
                text = message,
                fontSize = 15.sp,
                fontFamily = Lexend,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            ) }
        )
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen()
}

@Composable
fun LoadingScreen() {
    Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}
