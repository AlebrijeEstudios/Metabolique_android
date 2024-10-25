package com.alebrije_estudios.metabolique.navegation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.ui.LoginScreen
import com.alebrije_estudios.metabolique.login.ui.LoginViewModel
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileScreen
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileViewModel
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountScreen
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountViewModel
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountScreen
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountViewModel
import com.alebrije_estudios.metabolique.ui.LoadingScreen

@Composable
fun LoginNavigation(
    loginViewModel: LoginViewModel,
    registerAccountViewModel: RegisterAccountViewModel,
    myProfileViewModel: MyProfileViewModel,
    recoveryAccountViewModel: RecoveryAccountViewModel,
    doLogin: (AuthData) -> Unit
) {
    var isLoading: Boolean by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    if (!isLoading) {
        GradientBox()
        Scaffold(
            topBar = {},
            containerColor = Color.Transparent,
        ) { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding), contentAlignment = Alignment.Center
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {
                    composable(Screen.Login.route) {
                        myProfileViewModel.void()
                        registerAccountViewModel.void()
                        LoginScreen(
                            loginViewModel,
                            navController = navController,
                            isLoading = {isLoading = it}
                        ) {
                            doLogin(it)
                        }
                    }
                    composable(Screen.RecoverUser.route) {
                        RecoveryAccountScreen(recoveryAccountViewModel = recoveryAccountViewModel)
                    }
                    composable(Screen.CreateUser.route) {
                        RegisterAccountScreen(
                            viewModel = registerAccountViewModel,
                            navController = navController
                        )
                    }
                    composable(Screen.MyProfile.route) { backStackEntry ->
                        MyProfileScreen(
                            viewModel = myProfileViewModel,
                            name = backStackEntry.arguments?.getString("name") ?: "",
                            email = backStackEntry.arguments?.getString("email") ?: "",
                            password = registerAccountViewModel.password.value ?: "",
                            navController = navController,
                            isLoading = {isLoading = it}
                        ) {
                            doLogin(it)
                        }
                    }
                }
            }
        }
    }
    else
        LoadingScreen()
}
