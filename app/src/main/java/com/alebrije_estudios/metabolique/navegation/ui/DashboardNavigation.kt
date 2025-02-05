package com.alebrije_estudios.metabolique.navegation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.dashboard.ui.DashboardScreen
import com.alebrije_estudios.metabolique.dashboard.ui.DashboardViewModel
import com.alebrije_estudios.metabolique.exercise.ui.ExerciseScreen
import com.alebrije_estudios.metabolique.exercise.ui.ExerciseViewModel
import com.alebrije_estudios.metabolique.exercise.ui.MonthlyMonitoringExerciseScreen
import com.alebrije_estudios.metabolique.feed.ui.FeedScreen
import com.alebrije_estudios.metabolique.feed.ui.FeedViewModel
import com.alebrije_estudios.metabolique.feed.ui.MonthlyMonitoringFoodScreen
import com.alebrije_estudios.metabolique.food_capture.ui.FoodCaptureScreen
import com.alebrije_estudios.metabolique.food_capture.ui.FoodCaptureViewModel
import com.alebrije_estudios.metabolique.habits.ui.HabitViewModel
import com.alebrije_estudios.metabolique.habits.ui.HabitsScreen
import com.alebrije_estudios.metabolique.habits.ui.MonthlyMonitoringHabitScreen
import com.alebrije_estudios.metabolique.habits.ui.MonthlyMonitoringHabitViewModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.medication.ui.MedicationScreen
import com.alebrije_estudios.metabolique.medication.ui.MedicationViewModel
import com.alebrije_estudios.metabolique.medication.ui.MonthlyMonitoringMedicationScreen
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileScreen
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileViewModel
import com.alebrije_estudios.metabolique.navegation.Screen
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountScreen
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountViewModel
import com.alebrije_estudios.metabolique.ui.theme.BackgroundColor
import com.alebrije_estudios.metabolique.ui.theme.PrimaryColor
import com.alebrije_estudios.metabolique.ui.theme.Typography
import java.time.LocalDate

@Composable
fun DashboardNavigation(
    viewModel: MainViewModel,
    registerAccountViewModel: RegisterAccountViewModel,
    myProfileViewModel: MyProfileViewModel,
    dashboardViewModel: DashboardViewModel,
    feedViewModel: FeedViewModel,
    exerciseViewModel: ExerciseViewModel,
    medicationViewModel: MedicationViewModel,
    habitViewModel: HabitViewModel,
    foodCaptureViewModel: FoodCaptureViewModel,
    authData: AuthData,
    doLogout: () -> Unit
) {
    val showBackArrow by viewModel.showBackArrow.observeAsState(initial = false)
    val showTopBar by viewModel.showTopBar.observeAsState(initial = false)
    val showNavbar by viewModel.showNavbar.observeAsState(initial = false)
    val title by viewModel.title.observeAsState(initial = "")
    val navController = rememberNavController()
    GradientBox()
    Scaffold(
        topBar = {
            if (showTopBar)
                TopBar(showBackArrow = showBackArrow, title = title) {
                    navController.popBackStack()
                }
        },
        containerColor = Color.Transparent,
        bottomBar = {
            if (showNavbar)
                BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.TopCenter
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard.route
            ) {
                composable(Screen.CreateUser.route) {
                    viewModel.showViews(TopBarShow.HIDE_ALL)
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
                        //name = backStackEntry.arguments?.getString("name") ?: "",
                        //email = backStackEntry.arguments?.getString("email") ?: "",
                        password = registerAccountViewModel.password.value ?: "",
                        isEditUser = true,
                        //navController = navController,
                        isLoading = {},
                        doLogout = { doLogout() },
                        authData = authData
                    ) {}
                }
                composable(Screen.Dashboard.route) {
                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR)
                    //myProfileViewModel.void()
                    registerAccountViewModel.void()
                    DashboardScreen(
                        dashboardViewModel,
                        navController,
                        Modifier.align(Alignment.TopStart)
                    )
                }
                composable(Screen.Feed.route) {
                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR_TITLE)
                    if (title != stringResource(id = R.string.title_food)) {
                        viewModel.SetTitle(id = R.string.title_food)
                    }
//                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR)
                    FeedScreen(viewModel = feedViewModel, navController)
                }
                composable(Screen.CapturedFood.route) {
                    viewModel.showViews(TopBarShow.SHOW_ALL)
                    FoodCaptureScreen(viewModel = foodCaptureViewModel,date = feedViewModel.date.value!!)
                }
                composable(Screen.Exercises.route) {
                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR_TITLE)
                    if (title != stringResource(id = R.string.title_exercises)) {
                        viewModel.SetTitle(id = R.string.title_exercises)
                        exerciseViewModel.getListOfExercises(authData)
                    }
//                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR)
                    ExerciseScreen(viewModel = exerciseViewModel, authData, navController)
                }
                composable(Screen.Medication.route) {
                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR_TITLE)
                    if (title != stringResource(id = R.string.title_medication)) {
                        viewModel.SetTitle(id = R.string.title_medication)
                        medicationViewModel.getListMedications(authData)
                    }
//                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR)
                    MedicationScreen(
                        medicationViewModel = medicationViewModel,
                        authData = authData,
                        navController = navController
                    )
                }
                composable(Screen.Habits.route) {
                    viewModel.showViews(TopBarShow.SHOW_NAR_BAR_TITLE)
                    if (title != stringResource(id = R.string.title_habits)) {
                        viewModel.SetTitle(id = R.string.title_habits)
                        habitViewModel.getHabits(authData)
                    }
                    //viewModel.showViews(TopBarShow.SHOW_NAR_BAR)
                    HabitsScreen(habitViewModel, authData, navController)
                }
                composable(Screen.MonthlyMonitoringHabits.route) {
                    viewModel.showViews(TopBarShow.SHOW_ALL)
                    if(title != stringResource(id = R.string.title_monthly_monitoring)){
                        viewModel.SetTitle(id = R.string.title_monthly_monitoring)
                    }
                    MonthlyMonitoringHabitScreen(
                        date = LocalDate.now(),
                        monthlyMonitoringHabitViewModel = MonthlyMonitoringHabitViewModel()
                    )
                }
                composable(Screen.MonthlyMonitoringExercises.route) {
                    viewModel.showViews(TopBarShow.SHOW_ALL)
                    if (title != stringResource(id = R.string.title_monthly_monitoring)) {
                        viewModel.SetTitle(id = R.string.title_monthly_monitoring)

                    }
                    MonthlyMonitoringExerciseScreen(month = LocalDate.now(), modifier = Modifier)
                }
                composable(Screen.MonthlyMonitoringMedication.route) {
                    viewModel.showViews(TopBarShow.SHOW_ALL)
                    if (title!= stringResource(id = R.string.title_monthly_monitoring)) {
                        viewModel.SetTitle(id = R.string.title_monthly_monitoring)
                    }
                    MonthlyMonitoringMedicationScreen(month = LocalDate.now())
                }
                composable(Screen.MonthlyMonitoringFood.route) {
                    viewModel.showViews(TopBarShow.SHOW_ALL)
                    if (title!= stringResource(id = R.string.title_monthly_monitoring)) {
                        viewModel.SetTitle(id = R.string.title_monthly_monitoring)
                    }
                    MonthlyMonitoringFoodScreen(month = LocalDate.now())
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    navController.currentDestination?.route = Screen.Dashboard.route
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
        BottomBar(navController)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Image(painter = painterResource(id = R.drawable.background_menu), contentDescription = "")
    BottomAppBar(
        containerColor = Color.Transparent,
        //containerColor = Color(0xFFF3F5F5),
        //contentColor = PrimaryColor,
        //contentPadding = PaddingValues(horizontal = 16.dp),
        //tonalElevation = 16.dp,
        //modifier = Modifier.shadow(16.dp)
    ) {
//        NavigationBarItem(
//            modifier = Modifier.size(32.dp),
//            colors = NavigationBarItemDefaults.colors(
//                selectedIconColor = DefaultColor,
//                unselectedIconColor = PrimaryColor,
//                indicatorColor = PrimaryColor
//            ),
//            selected = navController.currentDestination?.route == Screen.Dashboard.route,
//            onClick = {
//                if (navController.currentDestination?.route != Screen.Dashboard.route){
//                navController.navigate(Screen.Dashboard.route)
//            } },
//            icon = {
//                Icon(
//                    modifier = Modifier.size(32.dp),
//                    painter = painterResource(id = R.drawable.ic_option_home),
//                    contentDescription = ""
//                )
//            })
//        NavigationBarItem(
//            modifier = Modifier.size(32.dp),
//            colors = NavigationBarItemDefaults.colors(
//                selectedIconColor = DefaultColor,
//                unselectedIconColor = PrimaryColor,
//                indicatorColor = PrimaryColor
//            ),
//            selected = navController.currentDestination?.route == Screen.Exercises.route,
//            onClick = {
//                if (navController.currentDestination?.route != Screen.Exercises.route){
//                navController.navigate(Screen.Exercises.route)
//            } },
//            icon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_option_exercise),
//                    modifier = Modifier.size(32.dp),
//                    contentDescription = ""
//                )
//            })
//        NavigationBarItem(
//            modifier = Modifier.size(32.dp),
//            colors = NavigationBarItemDefaults.colors(
//                selectedIconColor = DefaultColor,
//                unselectedIconColor = PrimaryColor,
//                indicatorColor = PrimaryColor
//            ),
//            selected = navController.currentDestination?.route == Screen.Feed.route,
//            onClick = {
//                if (navController.currentDestination?.route != Screen.Feed.route){
//                navController.navigate(Screen.Feed.route)
//            } },
//            icon = {
//                Icon(
//                    modifier = Modifier.size(32.dp),
//                    painter = painterResource(id = R.drawable.ic_option_feed),
//                    contentDescription = "")
//            })
//        NavigationBarItem(
//            modifier = Modifier.size(32.dp),
//            colors = NavigationBarItemDefaults.colors(
//                selectedIconColor = DefaultColor,
//                unselectedIconColor = PrimaryColor,
//                indicatorColor = PrimaryColor
//            ),
//            selected = navController.currentDestination?.route == Screen.Medication.route,
//            onClick = {
//                if (navController.currentDestination?.route != Screen.Medication.route){
//                navController.navigate(Screen.Medication.route)
//            } },
//            icon = {
//                Icon(
//                    modifier = Modifier.size(32.dp),
//                    painter = painterResource(id = R.drawable.ic_option_medication),
//                    contentDescription = ""
//                )
//            })
//        NavigationBarItem(
//            modifier = Modifier.size(32.dp),
//            colors = NavigationBarItemDefaults.colors(
//                selectedIconColor = DefaultColor,
//                unselectedIconColor = PrimaryColor,
//                indicatorColor = PrimaryColor
//            ),
//            selected = navController.currentDestination?.route == Screen.Habits.route,
//            onClick = {
//                if (navController.currentDestination?.route != Screen.Habits.route){
//                navController.navigate(Screen.Habits.route)
//            } },
//            icon = {
//                Icon(
//                    modifier = Modifier.size(32.dp),
//                    painter = painterResource(id = R.drawable.ic_option_habit),
//                    contentDescription = ""
//                )
//            })
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            NavItemButton(
                selected = navController.currentDestination?.route == Screen.Dashboard.route,
                onClicked = {
                    if (navController.currentDestination?.route != Screen.Dashboard.route) {
                        navController.navigate(Screen.Dashboard.route)
                    }
                }) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_option_home),
                    contentDescription = ""
                )
            }
            NavItemButton(
                selected = navController.currentDestination?.route == Screen.Feed.route,
                onClicked = {
                    if (navController.currentDestination?.route != Screen.Feed.route)
                        navController.navigate(Screen.Feed.route)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_option_feed),
                    contentDescription = ""
                )
            }

            NavItemButton(
                selected = navController.currentDestination?.route == Screen.Exercises.route,
                onClicked = {
                    if (navController.currentDestination?.route != Screen.Exercises.route)
                        navController.navigate(Screen.Exercises.route)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_option_exercise),
                    contentDescription = ""
                )
            }
            NavItemButton(
                selected = navController.currentDestination?.route == Screen.Medication.route,
                onClicked = {
                    if (navController.currentDestination?.route != Screen.Medication.route)
                        navController.navigate(Screen.Medication.route)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_option_medication),
                    contentDescription = ""
                )
            }
            NavItemButton(
                selected = navController.currentDestination?.route == Screen.Habits.route,
                onClicked = {
                    if (navController.currentDestination?.route != Screen.Habits.route)
                        navController.navigate(Screen.Habits.route)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_option_habit),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun NavItemButton(
    selected: Boolean,
    onClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = if (selected) PrimaryColor else Color.Unspecified,
            contentColor = if (selected) Color.White else PrimaryColor
        ),
        modifier = Modifier
            .size(64.dp)
            .clip(shape = CircleShape)
            .padding(4.dp),
        onClick = { onClicked() }
    ) {
        content()
    }
}

@Composable
fun GradientBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BackgroundColor, BackgroundColor.copy(0.5f), Color.Transparent)
                )
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(showBackArrow: Boolean, title: String = "", onClicked: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = PrimaryColor
        ),
        navigationIcon = {
            if (showBackArrow)  // Show back arrow only when necessary
                IconButton(onClick = { onClicked() }) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
        },
        title = {
            Text(
                text = title,
                style = Typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        })
}