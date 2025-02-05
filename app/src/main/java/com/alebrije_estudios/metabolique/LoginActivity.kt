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
import com.alebrije_estudios.metabolique.login.ui.LoginViewModel
import com.alebrije_estudios.metabolique.my_profile.ui.MyProfileViewModel
import com.alebrije_estudios.metabolique.navegation.ui.LoginNavigation
import com.alebrije_estudios.metabolique.recobery_account.ui.RecoveryAccountViewModel
import com.alebrije_estudios.metabolique.register_account.ui.RegisterAccountViewModel
import com.alebrije_estudios.metabolique.ui.theme.MetaboliqueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
        private val recoveryAccountViewModel: RecoveryAccountViewModel by viewModels() // add the recovery account
        private val loginViewModel: LoginViewModel by viewModels() // add LoginViewModel
        private val registerAccountViewModel: RegisterAccountViewModel by viewModels() // add RegisterAccount
        private val myProfileViewModel: MyProfileViewModel by viewModels() // add MyProfile

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            val newIntent = Intent(this, DashboardActivity::class.java)
            setContent {
                localeSelection(LocalContext.current, Locale("es").toLanguageTag())
               // window.navigationBarColor = if(isSystemInDarkTheme()) Color.BLACK else Color.TRANSPARENT
                MetaboliqueTheme (darkTheme = false, dynamicColor = false){
                    LoginNavigation(
                        loginViewModel = loginViewModel,
                        registerAccountViewModel = registerAccountViewModel,
                        recoveryAccountViewModel = recoveryAccountViewModel,
                        myProfileViewModel = myProfileViewModel
                    ){ auth ->
                        Log.i("Login Metabolique", auth.toString())
                        auth.createPreferences(getSharedPreferences("User",MODE_PRIVATE))
                        /*newIntent.putExtra("token", auth.token.replace("Bearer ",""))
                        newIntent.putExtra("accountID", auth.accountID)*/
                        startActivity(newIntent)
                        finish()
                    }
                }
            }
        }


}