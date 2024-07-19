package com.alebrije_estudios.metabolique.login.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.composable.DefaultButton
import com.alebrije_estudios.metabolique.composable.DefaultTextField
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.theme.MetaboliqueTheme


@Preview(showBackground = true, locale = "es")
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
    val isEnabledLogin: Boolean by loginViewModel.isEnabledLogin.observeAsState(false)
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
            PasswordField(password =password, imeAction = ImeAction.Done) { loginViewModel.onChangedUser(email, it) }
            Spacer(modifier = Modifier.size(8.dp))
            RecoverAccount { navController.navigate(Screen.RecoverUser.route) }
            Spacer(modifier = Modifier.size(12.dp))
            LoginButton(isEnabledLogin) { loginViewModel.login() }
            Spacer(modifier = Modifier.size(40.dp))
            TextNewUser()
            Spacer(modifier = Modifier.size(8.dp))
            RegisterButton { navController.navigate(Screen.CreateUser.route) }
        }
    }
}

@Composable
fun RegisterButton(onClicked: () -> Unit) {
    DefaultButton(label = stringResource(R.string.button_create_account)) {
        onClicked()
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
            text = stringResource(R.string.button_recovery_account),
            color = Color(0XFFbecb57),
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginButton(enabled: Boolean, onClicked: () -> Unit) {
    DefaultButton(
        label = stringResource(id = R.string.button_sign_in),
        enabled = enabled
    ) {
        onClicked()
    }
}

@Composable
fun PasswordField(
    password: String,
    label: String = stringResource(id = R.string.label_password),
    imeAction: ImeAction = ImeAction.Next,
    onChangeText: (String) -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    DefaultTextField(
        value = password,
        label = label,
        imeAction = imeAction,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardType = KeyboardType.Password,
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = stringResource(id = R.string.content_description_show_icon)
                )
            }
        },
    ) {
        onChangeText(it)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmailField(email: String, onChangeText: (String) -> Unit) {
    DefaultTextField(
        value = email,
        label = stringResource(id = R.string.label_email),
        keyboardType = KeyboardType.Email
    ) {
        onChangeText(it)
    }
}


@Composable
fun HeaderLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.icon_og),
        contentDescription = stringResource(id = R.string.content_description_logo),
        modifier = modifier
            .size(128.dp)
            .clip(CircleShape)
            .background(Color(0xFF00273D))
    )
}
