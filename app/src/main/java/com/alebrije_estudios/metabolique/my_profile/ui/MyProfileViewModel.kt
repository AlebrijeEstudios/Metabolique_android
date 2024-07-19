package com.alebrije_estudios.metabolique.my_profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alebrije_estudios.metabolique.navegation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
@HiltViewModel
class MyProfileViewModel @Inject constructor(): ViewModel() {
    private val _isEnabled:MutableLiveData<Boolean> = MutableLiveData()
    val isEnabled: LiveData<Boolean> = _isEnabled
    private val _dateBirth: MutableLiveData<Date?> = MutableLiveData()
    val dateBirth: LiveData<Date?> = _dateBirth
    private val _gender: MutableLiveData<String> = MutableLiveData()
    val gender: LiveData<String> = _gender
    private val _stature:MutableLiveData<Float> = MutableLiveData()
    val stature: LiveData<Float> = _stature
    private val _protocol:MutableLiveData<String> = MutableLiveData()
    val protocol: LiveData<String> = _protocol
    val protocols: MutableList<String> = mutableListOf("Ninguno")
    val statures:MutableList<String> = mutableListOf("1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0", "2.1", "2.2")

    fun void(){
        _gender.value = ""
        _dateBirth.value = null
        _stature.value = 0f
        _protocol.value = ""
        _isEnabled.value = false
    }
    fun saveDataUser(navController: NavController) {
        navController.popBackStack()
        navController.popBackStack()
        navController.popBackStack()
        navController.navigate(Screen.Dashboard.route)
    }

    fun onValidated(dateBirth: Date?,gender:String,stature:Float,protocol:String) {
        _dateBirth.value = dateBirth
        _gender.value = gender
        _stature.value = stature
        _protocol.value = protocol
        _isEnabled.value = _dateBirth.value!= null && gender.isNotBlank() && stature > 0 && protocol.isNotBlank()
    }
}
