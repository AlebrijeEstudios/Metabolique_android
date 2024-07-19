package com.alebrije_estudios.metabolique

import android.app.LocaleManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.core.os.LocaleListCompat
import com.alebrije_estudios.metabolique.login.ui.LoginViewModel
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileViewModel
import com.alebrije_estudios.metabolique.navegation.ui.MainScreen
import com.alebrije_estudios.metabolique.navegation.ui.MainViewModel
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountViewModel
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountViewModel
import com.alebrije_estudios.metabolique.ui.theme.MetaboliqueTheme
import dagger.hilt.android.AndroidEntryPoint

const val EMAIL = "ayuda@vidasana.com"
const val PHONE = "+52(81) 1234 5678"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels() // add MainScreenViewModel here
    private val recoveryAccountViewModel: RecoveryAccountViewModel by viewModels() // add the recovery account
    private val loginViewModel: LoginViewModel by viewModels() // add LoginViewModel
    private val registerAccountViewModel: RegisterAccountViewModel by viewModels() // add RegisterAccount
    private val myProfileViewModel: MyProfileViewModel by viewModels() // add MyProfile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            localeSelection(LocalContext.current, Locale("es").toLanguageTag())
            window.navigationBarColor = if(isSystemInDarkTheme()) Color.BLACK else Color.TRANSPARENT
            MetaboliqueTheme (darkTheme = false, dynamicColor = false){
                MainScreen(
                    viewModel = mainViewModel,
                    loginViewModel = loginViewModel,
                    registerAccountViewModel = registerAccountViewModel,
                    myProfileViewModel = myProfileViewModel,
                    recoveryAccountViewModel = recoveryAccountViewModel
                )
            }
        }
    }
}
fun localeSelection(context: Context, localeTag: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(localeTag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(localeTag)
        )
    }
}

