package com.alebrije_estudios.metabolique.food_capture.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.ui.DefaultDropdownMenu
import com.alebrije_estudios.metabolique.feed.ui.getDayMonthValueYear
import com.alebrije_estudios.metabolique.my_profile.ui.ButtonSave
import com.alebrije_estudios.metabolique.ui.theme.DefaultColor
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@Preview(showBackground = true, locale = "es")
@Composable
fun FoodCaptureScreenPreview() {
    FoodCaptureScreen(FoodCaptureViewModel(), LocalDate.now())
}

@Composable
fun FoodCaptureScreen(viewModel: FoodCaptureViewModel, date: LocalDate) {
    val listFoods: List<FoodCModel> by remember { mutableStateOf(viewModel.getFoods(LocalDate.now())) }

    //val permissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val showModalBottomSheet by viewModel.showModalBottomSheet.observeAsState(initial = false)
    val srcImage:Uri by  viewModel.srcImage.observeAsState(initial = Uri.EMPTY)
    val search:String by viewModel.search.observeAsState(initial = "")
    ModalAddFood(showModalBottomSheet, listOf("3","2","1","1/2","1/3","1/4","1/8" )){viewModel.hideModalBottomSheet()}
    DialogSelectPickerMedia(false,srcImage){viewModel.updateSrcImage(it)}
    Column(Modifier.fillMaxWidth()) {
        HeaderDateTime(date, viewModel.time){
            viewModel.time = it
        }
        Spacer(modifier = Modifier.size(16.dp))
        NameText(Modifier.align(Alignment.CenterHorizontally))
        SearchFood(search, listOf("Manzana", "Pera", "Plátano", "Naranja", "Pollo", "Refresco", "Fresa", "Piña", "Jugo", "Mandarina", "Chocolate", "Huevo a la mexicana", "Frambuesa"), {viewModel.updateSearch(it)}){
            viewModel.showModalBottomSheet()
        }
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(listFoods) { index, food ->
                ItemFood(food, onUpdate = {viewModel.updateFood(it,index)}, onDelete = {viewModel.removeFood(food)})
                HorizontalDivider()
            }
        }
        LevelSatisfactionDropdown(""){

        }
        Spacer(modifier = Modifier.size(8.dp))
        LinkedEmotionsDropdown(""){

        }
        Spacer(modifier = Modifier.size(8.dp))
        if(!srcImage.path.isNullOrEmpty()){
            AsyncImage(model = srcImage, contentDescription = "", modifier = Modifier.size(160.dp).align(Alignment.CenterHorizontally))
        }
        else{
            DefaultButton(label = stringResource(id = R.string.button_add_image), enabled = true, modifier = Modifier.fillMaxWidth()) {
                //pickMedia.launch("image/*")
              /* if (permissionState.status.isGranted)

                    pickMedia.launch("image/*")
                else
                    permissionState.launchPermissionRequest()
*/*/
            }

        }
        DefaultButton(label = stringResource(id = R.string.button_save_change), enabled = false, modifier = Modifier.fillMaxWidth()) {

        }
        Spacer(modifier = Modifier.size(32.dp))
    }
}

 @OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DialogSelectPickerMedia(show:Boolean, srcImage: Uri, selected: (Uri) -> Unit) {
    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {uri ->
        if(uri == null) return@rememberLauncherForActivityResult
        selected(uri)
    }
    if (show)
        Dialog(onDismissRequest = {}){
            Row(
                Modifier
                    //.shape(ShapeDefaults.ExtraLarge)
                    .background(color = DefaultColor, shape = ShapeDefaults.ExtraLarge)
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    ) {
                Button(
                    onClick = {},
                    shape = ShapeDefaults.ExtraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = PrimaryColor
                    ),
                    contentPadding =  PaddingValues(8.dp)
                ){
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
                        Text(text = stringResource(id = R.string.label_select))
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {},
                    shape = ShapeDefaults.ExtraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = PrimaryColor
                    ),
                    contentPadding =  PaddingValues(8.dp)
                ){
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
                        Text(text = stringResource(id = R.string.label_select))
                    }
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalAddFood(show: Boolean,listPortion: List<String>,onDismissRequest: () -> Unit) {
    if(show){
        ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        LazyColumn {
            items(listPortion){
                Text(text = it, modifier = Modifier.clickable {
                    onDismissRequest()
                })
            }
         }
    }
    }
}

@Composable
fun LinkedEmotionsDropdown(value: String, onChanged: (String) -> Unit) {
    DefaultDropdownMenu(value = value, label = stringResource(id = R.string.label_emotional_state), dropItemsText = listOf("")) {
        onChanged(it)
    }
}

@Composable
fun LevelSatisfactionDropdown(value: String, onChanged: (String) -> Unit) {
    DefaultDropdownMenu(value = value, label = stringResource(id = R.string.label_level_satisfatory), dropItemsText = listOf("")) {
        onChanged(it)
    }
}

@Composable
fun ItemFood(food:FoodCModel,onUpdate:(FoodCModel) -> Unit, onDelete: () ->Unit) {
    Row(Modifier.fillMaxWidth()){
        Column (
            Modifier
                .weight(1f)
                .clickable { onUpdate(food) }
        ){
            Text(
                text = food.title,
                style = Typography.bodyMedium,
                color = PrimaryColor
            )
            Spacer(modifier = Modifier.size(4.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text =food.portion + " " + food.unit,
                    style = Typography.bodySmall
                )
                Text(
                    text = food.calories.toString() + " kcal",
                    style = Typography.bodySmall
                )
            }
        }
        IconButton(onClick = {onDelete() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                tint = PrimaryColor
            )
        }
    }
}

@Composable
fun SearchFood(value:String,listSearch:List<String>, onChanged: (String) -> Unit, onSeleted:(String) -> Unit) {
    Column {
        OutlinedTextField(
            value = value,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            onValueChange = {
                onChanged(it)
            },
            placeholder = {
                Text(stringResource(id = R.string.label_search_food))
            },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 8.dp)){
                    if(value.isNotEmpty())
                        IconButton(onClick = { onChanged("") },
                            modifier =
                            Modifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        ){
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "",
                                tint = PrimaryColor
                            )
                        }
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "",
                        tint = LabelColor
                    )
                }
            }
        )
        DropdownMenu(
            expanded = value.isNotEmpty(),
            properties = PopupProperties(
                focusable = false,
                excludeFromSystemGesture = false,
                clippingEnabled = false
            ),
            onDismissRequest = {
//            onChanged("")
        },
            containerColor = DefaultColor,
            modifier = Modifier.fillMaxWidth()
        ) {
            listSearch.filter { it.lowercase(Locale.getDefault()).contains(value.lowercase(Locale.getDefault())) }.forEach{
                food ->
                Text(food, modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        onSeleted(food)
                        onChanged(food)
                    })
            }
        }
    }
}

@Composable
fun NameText(modifier: Modifier) {
    Text(
        stringResource(id = R.string.label_name_food),
        modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = PrimaryColor,
        style = Typography.titleMedium
    )
}


@Composable
fun HeaderDateTime(date: LocalDate, time: LocalTime, onUpdateTime:(LocalTime) -> Unit) {
    var  showTimePicker by remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = date.getDayMonthValueYear(),
            color = PrimaryColor,
            style = Typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            time.toFormat12(),
            color = PrimaryColor,
            style = Typography.bodySmall)
        IconButton(onClick = { showTimePicker = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_date),
                contentDescription = "",
                tint = PrimaryColor
            )
        }
        TimePickerDialog(showTimePicker, {showTimePicker = false}) { onUpdateTime(it) }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

fun LocalTime.toFormat12(): String {
    val module = this.hour%12
    return "${if(module == 0)"12" else hour%12}:${minute.toString().padStart(2,'0')} ${if(hour >=12) "PM" else "AM"}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    showDialog: Boolean = true,
    onDismissRequest: () -> Unit,
    onChanged: (LocalTime) -> Unit
) {
    val stateTime = rememberTimePickerState()
    if (showDialog) {
        DatePickerDialog(onDismissRequest = { onDismissRequest() }, confirmButton = {
            ButtonSave(isEnabled = true) {
                onChanged(LocalTime.of(stateTime.hour, stateTime.minute))
            }
        }) {
            TimePicker(modifier = Modifier.align(Alignment.CenterHorizontally), state = stateTime)
        }
    }
}
