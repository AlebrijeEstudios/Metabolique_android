package com.alebrije_estudios.metabolique

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import com.alebrije_estudios.metabolique.dashboard.ui.DashboardViewModel
import com.alebrije_estudios.metabolique.exercise.ui.ExerciseViewModel
import com.alebrije_estudios.metabolique.feed.ui.FeedViewModel
import com.alebrije_estudios.metabolique.food_capture.ui.FoodCaptureViewModel
import com.alebrije_estudios.metabolique.habits.ui.HabitViewModel
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.ui.LoginViewModel
import com.alebrije_estudios.metabolique.medication.ui.MedicationViewModel
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileViewModel
import com.alebrije_estudios.metabolique.navegation.ui.DashboardNavigation
import com.alebrije_estudios.metabolique.navegation.ui.MainViewModel
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountViewModel
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountViewModel
import com.alebrije_estudios.metabolique.ui.theme.MetaboliqueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
        private val mainViewModel: MainViewModel by viewModels() // add MainScreenViewModel here
        private val registerAccountViewModel: RegisterAccountViewModel by viewModels() // add RegisterAccount
        private val myProfileViewModel: MyProfileViewModel by viewModels() // add MyProfile
        private val feedViewModel: FeedViewModel by viewModels() // add FeedViewModel
        private val exerciseViewModel: ExerciseViewModel by viewModels() // add ExerciseViewModel
        private val medicationViewModel: MedicationViewModel by viewModels() // add Medication
        private val habitViewModel: HabitViewModel by viewModels() // add
        private val dashboardViewModel: DashboardViewModel by viewModels() // add DashboardViewModel
        private val foodCaptureViewModel: FoodCaptureViewModel by viewModels() // add FoodCaptureViewModel
       /* private val authData:AuthData by lazy {

            AuthData(bundle?.getString("token")?:"", bundle?.getString("accountID")?:"")
        }*/
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
//            val bundle = intent?.extras
            // get a Preferences
            val newIntent = Intent(this, LoginActivity::class.java)
            val sharedPref = getSharedPreferences("User",MODE_PRIVATE)
            if(!sharedPref.getString("token", "")?.contains(".")!!){
                Log.i("token", sharedPref.getString("token", "")?:"")
                startActivity(newIntent)
                finish()
            }
            val authData = AuthData(sharedPref.getString("token", "")?:"", sharedPref.getString("accountID", "")?:"")
            setContent {
                localeSelection(LocalContext.current, Locale("es").toLanguageTag())
                window.navigationBarColor = if(isSystemInDarkTheme()) Color.BLACK else Color.TRANSPARENT
                MetaboliqueTheme (darkTheme = false, dynamicColor = false){
                    DashboardNavigation(
                        viewModel = mainViewModel,
                        registerAccountViewModel = registerAccountViewModel,
                        myProfileViewModel = myProfileViewModel,
                        feedViewModel = feedViewModel,
                        exerciseViewModel = exerciseViewModel,
                        medicationViewModel = medicationViewModel,
                        habitViewModel = habitViewModel,
                        dashboardViewModel = dashboardViewModel,
                        foodCaptureViewModel = foodCaptureViewModel,
                        authData = authData,
                    ){
                        with(sharedPref.edit()) {
                            putString("token", null)
                            putString("accountID", null)
                            commit()
                        }
                        startActivity(newIntent)
                        finish()
                    }
                }
            }
        }
    }
