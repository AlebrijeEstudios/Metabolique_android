package com.alebrije_estudios.metabolique.navegation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.login.ui.LoginScreen
import com.alebrije_estudios.metabolique.login.ui.LoginViewModel
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileScreen
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileViewModel
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountScreen
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountViewModel
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountScreen
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    loginViewModel: LoginViewModel,
    registerAccountViewModel: RegisterAccountViewModel,
    myProfileViewModel: MyProfileViewModel,
    recoveryAccountViewModel: RecoveryAccountViewModel
) { // Add RecoveryAccountViewModel here
    val showBackArrow by viewModel.showBackArrow.observeAsState(initial = false)
    val showTopBar by viewModel.showTopBar.observeAsState(initial = false)
    val showNavbar by viewModel.showNavbar.observeAsState(initial = false)
    val title by viewModel.title.observeAsState(initial = "")
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            Box {
                GradientBox(Modifier.align(Alignment.TopStart))
                if (showTopBar)
                    TopBar(showBackArrow = showBackArrow, title = title) {
                        navController.popBackStack()
                    }
            }
        },
        containerColor = Color.White,
        snackbarHost = {
            if (showNavbar)
                TODO()
            //SnackbarHost()
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding), contentAlignment = Alignment.Center) {

            NavHost(
                navController = navController,
                startDestination = Screen.Login.route
            ) {
                composable(Screen.Login.route) {
                    viewModel.showViews(TopBarShow.HIDE_ALL)
                    myProfileViewModel.void()
                    registerAccountViewModel.void()
                    LoginScreen(loginViewModel, navController = navController)
                }
                composable(Screen.RecoverUser.route) {
                    viewModel.showViews(TopBarShow.SHOW_BACK_BUTTON)
                    viewModel.SetTitle(id = R.string.title_recovery_account)
                    RecoveryAccountScreen(recoveryAccountViewModel = recoveryAccountViewModel)
                }
                composable(Screen.CreateUser.route) {
                    viewModel.showViews(TopBarShow.SHOW_BACK_BUTTON)
                    viewModel.SetTitle(id = R.string.title_create_account)
                    RegisterAccountScreen(
                        viewModel = registerAccountViewModel,
                        navController = navController
                    )
                }
                composable(Screen.MyProfile.route) { backStackEntry ->
                    viewModel.showViews(TopBarShow.SHOW_BACK_BUTTON)
                    MyProfileScreen(
                        viewModel = myProfileViewModel,
                        name = backStackEntry.arguments?.getString("name")?:"",
                        email = backStackEntry.arguments?.getString("email")?:"",
                        navController
                    )
                }
                composable(Screen.Dashboard.route) {
                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR_TITLE)
                    myProfileViewModel.void()
                    registerAccountViewModel.void()
                    // DashboardScreen
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(showBackArrow: Boolean, title: String = "", onClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            if (showBackArrow)  // Show back arrow only when necessary
                IconButton(onClick = { onClicked() }) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
        },
        title = { Text(text = title) })
}