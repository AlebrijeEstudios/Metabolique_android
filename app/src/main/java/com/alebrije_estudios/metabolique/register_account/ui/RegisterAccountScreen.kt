package com.alebrije_estudios.metabolique.register_account.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.composable.DefaultButton
import com.alebrije_estudios.metabolique.composable.DefaultTextField
import com.alebrije_estudios.metabolique.login.ui.PasswordField
import com.alebrije_estudios.metabolique.login.ui.EmailField
import com.alebrije_estudios.metabolique.login.ui.HeaderLogo

@Preview(showBackground = true, locale = "es")
@Composable
fun RegisterAccountPreview() {
    RegisterAccountScreen(RegisterAccountViewModel(), rememberNavController())
}


@Composable
fun RegisterAccountScreen(
    viewModel: RegisterAccountViewModel,
    navController: NavController
) {
    val name: String by viewModel.name.observeAsState("")
    val email: String by viewModel.email.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val confirmPassword: String by viewModel.confirmPassword.observeAsState("")
    val isEnabled: Boolean by viewModel.isEnabled.observeAsState(false)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        HeaderLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        NameField(name = name) {
            viewModel.onValidated(it, email, password, confirmPassword)
        }
        Spacer(modifier = Modifier.size(16.dp))
        EmailField(email = email) {
            viewModel.onValidated(name, it, password, confirmPassword)
        }
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(password = password) {
            viewModel.onValidated(name, email, it, confirmPassword)
        }
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(password = confirmPassword, imeAction = ImeAction.Done, label = stringResource(id = R.string.label_confirm_password)) {
            viewModel.onValidated(name, email, password, it)
        }
        Spacer(modifier = Modifier.size(32.dp))
        RestrictText()
        Spacer(modifier = Modifier.size(32.dp))
        CreateAccountButton(isEnabled = isEnabled){
           viewModel.onSubmit(navController)
        }
    }
}

@Composable
fun CreateAccountButton(isEnabled: Boolean, onClicked:() -> Unit) {
    DefaultButton(
        label = stringResource(id = R.string.button_create_account),
        enabled = isEnabled
    ) {
        onClicked()
    }
}

@Composable
fun RestrictText() {
    Text(text = stringResource(id = R.string.text_message_restrictions_password))
}


@Composable
fun NameField(name: String, onChangeText: (String) -> Unit) {
    DefaultTextField(value = name, label = stringResource(id = R.string.label_name )){
        onChangeText(it)
    }
}
