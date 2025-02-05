package com.alebrije_estudios.metabolique.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.library.ErrorType
import com.alebrije_estudios.metabolique.library.getErrorMessage
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.ui.DefaultButton
import com.alebrije_estudios.metabolique.ui.DefaultTextField
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.ui.DefaultDialog
import com.alebrije_estudios.metabolique.ui.theme.LabelColor
import com.alebrije_estudios.metabolique.ui.theme.Lexend
import com.alebrije_estudios.metabolique.ui.theme.RedColor
import com.alebrije_estudios.metabolique.ui.theme.TextColor


//@Preview(showBackground = true, locale = "es")
//@Composable
//fun LoginPreview() {
//    val loginClient = Retrofit.Builder()
//        .baseUrl("")
//        .build()
//        .create(LoginClient::class.java)
//    Surface {
//        LoginScreen(LoginViewModel(LoginUseCase(LoginRepository(LoginService(loginClient)))), rememberNavController()){}
//    }
//}



@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController,isLoading:(Boolean)->Unit, doLogin: (AuthData) -> Unit){
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val isEnabledLogin: Boolean by loginViewModel.isEnabledLogin.observeAsState(false)
    val showMessageDialog: Boolean by loginViewModel.showMessageDialog.observeAsState(initial = false)
    val message: String by loginViewModel.message.observeAsState("")
    val codeErrorEmail: ErrorType by loginViewModel.codeErrorEmail.observeAsState(ErrorType.ERROR_OK)
    val codeErrorPassword: ErrorType by loginViewModel.codeErrorPassword.observeAsState(ErrorType.ERROR_OK)
    val codeError:ErrorType by  loginViewModel.codeError.observeAsState(ErrorType.ERROR_OK)
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
            EmailField(
                email = email,
                codeError= codeErrorEmail,
                onFocusOut = {
                    loginViewModel.checkEmail()
                }
            ) {
                loginViewModel.onChangedUser(it, password)
                loginViewModel.restcodeErrorEmail()
            }
            Spacer(modifier = Modifier.size(8.dp))
            PasswordField(
                password =password,
                codeError = codeErrorPassword,
                onFocusOut = {
                    loginViewModel.checkPassword()
                },
                imeAction = ImeAction.Done) {
                loginViewModel.onChangedUser(email, it)
                loginViewModel.restcodeErrorPassword()
            }
            Spacer(modifier = Modifier.size(8.dp))
            RecoverAccount { navController.navigate(Screen.RecoverUser.route) }
            Spacer(modifier = Modifier.size(12.dp))
            LoginButton(isEnabledLogin) { loginViewModel.login({isLoading(it)}){
                doLogin(it)
            } }
            DefaultDialog(show = showMessageDialog, title = "Error", message =message ) {
                loginViewModel.hiddenMessageDialog()
            }
            Spacer(modifier = Modifier.size(40.dp))
            TextNewUser()
            Spacer(modifier = Modifier.size(8.dp))
            RegisterButton { navController.navigate(Screen.CreateUser.route) }
        }
    }
}

@Composable
fun RegisterButton(onClicked: () -> Unit) {
    DefaultButton(label = stringResource(R.string.button_create_account), modifier = Modifier.fillMaxWidth()) {
        onClicked()
    }
}

@Composable
fun TextNewUser() {
    Text(
        text =stringResource(R.string.text_message_new_user),
        fontSize = 15.sp,
        fontFamily = Lexend,
        color = LabelColor
    )
}

@Composable
fun RecoverAccount(onClicked: () -> Unit) {
    TextButton(onClick = { onClicked() }) {
        Text(
            text = stringResource(R.string.button_recovery_account),
            color = TextColor,
            fontSize = 15.sp,
            fontFamily = Lexend,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginButton(enabled: Boolean, onClicked: () -> Unit) {
    DefaultButton(
        label = stringResource(id = R.string.button_sign_in),
        modifier = Modifier.fillMaxWidth(),
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
    codeError: ErrorType = ErrorType.ERROR_OK,
    onFocusOut: () -> Unit = {},
    onChangeText: (String) -> Unit
) {
    var initFocus: Boolean by remember { mutableStateOf(false) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    DefaultTextField(
        value = password,
        label = label,
        imeAction = imeAction,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardType = KeyboardType.Password,
        isError = codeError != ErrorType.ERROR_OK,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    initFocus = true
                }
                else if(initFocus){
                    onFocusOut()
                }
            },
        supportingText = {
           if (codeError != ErrorType.ERROR_OK){
               Text(text = codeError.getErrorMessage(label),
                   color = RedColor,
                   fontSize = 12.sp,
                   fontFamily = Lexend
               )
           }

        },
        trailingIcon = {
            //se quito el boton de mostrar el password
            /*IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = stringResource(id = R.string.content_description_show_icon)
                )
            }*/
        },
    ) {
        onChangeText(it)
    }
}

@Composable
fun EmailField(
    email: String,
    codeError:ErrorType = ErrorType.ERROR_OK,
    imeAction: ImeAction = ImeAction.Next,
    onFocusOut: () -> Unit = {},
    onChangeText: (String) -> Unit,
) {
    var initFocus: Boolean by remember { mutableStateOf(false) }
    DefaultTextField(imeAction = imeAction,
        value = email,
        label = stringResource(id = R.string.label_email),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    initFocus = true
                }
                else if(initFocus){
                    onFocusOut()
                }
            },
        isError = ErrorType.ERROR_OK != codeError,
        supportingText = {
            if(codeError != ErrorType.ERROR_OK){
                Text(text = codeError.getErrorMessage(stringResource(id = R.string.label_email)),
                    color = RedColor,
                    fontSize = 12.sp,
                    fontFamily = Lexend
                )
            }
        },
        keyboardType = KeyboardType.Email
    ) {
        onChangeText(it)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HeaderLogoPreview(){
//    HeaderLogo(Modifier)
//}
@Composable
fun HeaderLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(id = R.string.content_description_logo),
        modifier = modifier
            .width(230.dp)
    )
}
