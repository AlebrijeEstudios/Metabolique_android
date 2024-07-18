package com.alebrije_estudios.metabolique.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.theme.MetaboliqueTheme


@Preview(showBackground = true, locale ="es")
@Composable
fun LoginPreview() {
    MetaboliqueTheme(false) {
        Surface {
            LoginScreen(LoginViewModel(), rememberNavController())
        }
    }
}


@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val isEnabledLogin:Boolean by loginViewModel.isEnabledLogin.observeAsState(false)
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            HeaderLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.size(40.dp))
            EmailField(email) { loginViewModel.onChangedUser(it, password) }
            Spacer(modifier = Modifier.size(8.dp))
            PasswordField(password) { loginViewModel.onChangedUser(email, it) }
            Spacer(modifier = Modifier.size(8.dp))
            RecoverAccount { navController.navigate(Screen.RecoverUser.route) }
            Spacer(modifier = Modifier.size(12.dp))
            LoginButton(isEnabledLogin) { loginViewModel.login() }
            Spacer(modifier = Modifier.size(40.dp))
            TextNewUser()
            Spacer(modifier = Modifier.size(8.dp))
            RegisterButton {navController.navigate(Screen.CreateUser.route)}
        }
    }
}


@Composable
fun RegisterButton(onClicked: () -> Unit) {
    Button({ onClicked() }, modifier = Modifier.fillMaxWidth()) {

        Text(stringResource(R.string.button_create_account))
    }


}

@Composable
fun TextNewUser() {
    Text(stringResource(R.string.text_message_new_user))
}

@Composable
fun RecoverAccount(onClicked: () -> Unit) {
    TextButton(onClick = { onClicked() }) {
        Text(
            text= stringResource(R.string.button_recovery_account),
            color = Color(0XFFbecb57),
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginButton(enabled: Boolean, onClicked: () -> Unit) {
    Button(onClick = { onClicked() }, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.button_sign_in))
    }
}

@Composable
fun PasswordField(password: String, label:String = stringResource(id = R.string.label_password), onChangeText: (String) -> Unit) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onChangeText(it) },
        label = {
            Text(text = label)
        },
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = stringResource(id = R.string.content_description_show_icon)
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EmailField(email: String, onChangeText: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { onChangeText(it) },
        label = {
            Text(text = stringResource(id = R.string.label_email))
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

        )
}


@Composable
fun HeaderLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = stringResource(id = R.string.content_description_logo),
        modifier = modifier.size(128.dp)
    )
}
