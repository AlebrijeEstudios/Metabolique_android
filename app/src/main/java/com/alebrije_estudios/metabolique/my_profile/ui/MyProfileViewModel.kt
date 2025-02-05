package com.alebrije_estudios.metabolique.my_profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alebrije_estudios.metabolique.library.ERROR_ANY
import com.alebrije_estudios.metabolique.library.ErrorType
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.my_profile.domain.DeleteUserUseCase
import com.alebrije_estudios.metabolique.my_profile.domain.GetProfileUseCase
import com.alebrije_estudios.metabolique.register_account.domain.RegisterAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): ViewModel() {
    private val _name:MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name
    private val _email:MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String> = _email
    val weights: List<String> = listOf("40")
    private val _isEnabled:MutableLiveData<Boolean> = MutableLiveData()
    val isEnabled: LiveData<Boolean> = _isEnabled
    private val _dateBirth: MutableLiveData<LocalDate?> = MutableLiveData()
    val dateBirth: LiveData<LocalDate?> = _dateBirth
    private val _gender: MutableLiveData<String> = MutableLiveData()
    val gender: LiveData<String> = _gender
    private val _stature:MutableLiveData<Float> = MutableLiveData()
    val stature: LiveData<Float> = _stature
    private val _weight:MutableLiveData<Float> = MutableLiveData()
    val weight: LiveData<Float> = _weight
    private val _protocol:MutableLiveData<String> = MutableLiveData()
    val protocol: LiveData<String> = _protocol
    val protocols: MutableList<String> = mutableListOf("Ninguno")
    val statures:MutableList<String> = mutableListOf("140", "150", "160", "170", "180", "190", "200", "210", "220")
    private val _showMessageDialog:MutableLiveData<Boolean> = MutableLiveData()
    val showMessageDialog: LiveData<Boolean> = _showMessageDialog
    private val _message:MutableLiveData<ErrorType> = MutableLiveData()
    val message: LiveData<ErrorType> = _message
    private val _showDialogDeleteAccount:MutableLiveData<Boolean> = MutableLiveData()
    val showDialogDeleteAccount: LiveData<Boolean> = _showDialogDeleteAccount
    fun void(){
        _gender.value = ""
        _dateBirth.value = null
        _stature.value = 0f
        _protocol.value = ""
        _isEnabled.value = false
    }
    fun saveDataUser(name:String, email:String, password:String,isLoading:(Boolean) -> Unit, doLogin:(AuthData) -> Unit) {
        /*navController.popBackStack()
        navController.popBackStack()
        navController.popBackStack()*/
        isLoading(true)
        viewModelScope.launch {
            try{
            val auth = registerAccountUseCase(
            UserModel(
                name = name,
                lastName = "",
                email = email,
                password = password,
                birthDate = _dateBirth.value?:  Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate(),
                gender = _gender.value?:"",
                weight = _weight.value?.toFloat()?:0.0,
                status = _stature.value?.toFloat()?:0.0,
                protocolToFollow = _protocol.value?:""
            ))
                if (auth == null) throw Exception("Error al Registrar Usuario")
                doLogin(auth)
            }
            catch (e: Exception){
                val error:ErrorType = ErrorType.fromName(e.message ?: "")
                _message.value = error
                _showMessageDialog.value = true
            }
            finally {
                isLoading(false)
            }
        }
       // navController.navigate(Screen.Dashboard.route)
    }

    fun onValidated(dateBirth: LocalDate?,gender:String,weight:Float,stature:Float,protocol:String) {
        _dateBirth.value = dateBirth
        _gender.value = gender
        _stature.value = stature
        _weight.value = weight
        _protocol.value = protocol
        _isEnabled.value = _dateBirth.value!= null && gender.isNotBlank() && stature > 0 && protocol.isNotBlank()
    }

    fun showDialogDeleteAccount() {
        _showDialogDeleteAccount.value = true
    }
    fun hiddenDialogDeleteAccount() {
        _showDialogDeleteAccount.value = false
    }

    fun hiddenDialogMessage() {
        _showMessageDialog.value = false
    }

    fun deleteDataUser(authData: AuthData) {
        viewModelScope.launch {
            deleteUserUseCase(authData)
        }
    }
    fun getAccuntData(authData: AuthData) {
        viewModelScope.launch {
            val user = getProfileUseCase(authData)
            if (user != null) {
                _stature.value =  user.stature.toFloat()
                _weight.value = user.weight.toFloat()
                _protocol.value = user.protocolToFollow
                _gender.value = user.gender
                _dateBirth.value =  LocalDate.parse(user.birthdate)

            }
        }
    }

    fun setName(name: String) {
        _name.value = name
    }
    fun setEmail(email: String) {
        _email.value = email
    }
}
