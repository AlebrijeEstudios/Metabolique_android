package com.alebrije_estudios.metabolique.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alebrije_estudios.metabolique.R


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen(LoginViewModel())
}


@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val isEnabledLogin:Boolean by loginViewModel.isEnabledLogin.observeAsState(false)
    Box(
        Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White),
        contentAlignment = Alignment.Center
    ) {
        GradientBox(Modifier.align(Alignment.TopStart))
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
            RecoverAccount { loginViewModel.recoverAccount() }
            Spacer(modifier = Modifier.size(12.dp))
            LoginButton(isEnabledLogin) { loginViewModel.Login() }
            Spacer(modifier = Modifier.size(40.dp))
            TextNewUser()
            Spacer(modifier = Modifier.size(8.dp))
            RegisterButton {}
        }
    }
}

@Composable
fun GradientBox(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFeed572), Color.Transparent)
                )
            )
    )
}

@Composable
fun RegisterButton(onClicked: () -> Unit) {
    Button({ onClicked() }, modifier = Modifier.fillMaxWidth()) {
        Text("CREATE ACCOUNT")
    }


}

@Composable
fun TextNewUser() {
    Text("Don't have an account yet? Create one for free ")
}

@Composable
fun RecoverAccount(onClicked: () -> Unit) {
    TextButton(onClick = { onClicked() }) {
        Text(
            "Forgot your password?",
            color = Color(0XFFbecb57),
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginButton(enabled: Boolean, onClicked: () -> Unit) {
    Button(onClick = { onClicked() }, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text("SIGN IN")
    }
}

@Composable
fun PasswordField(password: String, onChangeText: (String) -> Unit) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onChangeText(it) },
        label = {
            Text(text = "Password")
        },
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Visibility Button"
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
            Text(text = "Email")
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

        )
}


@Composable
fun HeaderLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Logo the Metabolique",
        modifier = modifier.size(128.dp)
    )
}
