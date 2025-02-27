package com.alebrije_estudios.metabolique.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alebrije_estudios.metabolique.library.ErrorType
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password:LiveData<String> = _password
    private val _email: MutableLiveData<String> = MutableLiveData()
    val email:LiveData<String> = _email
    private val _isEnabledLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isEnabledLogin: LiveData<Boolean> = _isEnabledLogin
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message
    private val _showMessageDialog: MutableLiveData<Boolean> = MutableLiveData()
    val showMessageDialog: LiveData<Boolean> = _showMessageDialog
    private val _codeErrorEmail:MutableLiveData<ErrorType> = MutableLiveData()
    val codeErrorEmail:LiveData<ErrorType> = _codeErrorEmail
    private val _codeErrorPassword:MutableLiveData<ErrorType> = MutableLiveData()
    val codeErrorPassword:LiveData<ErrorType> = _codeErrorPassword
    private val _codeError:MutableLiveData<ErrorType> = MutableLiveData()
    val codeError:MutableLiveData<ErrorType> = _codeError

    fun restcodeErrorEmail(){
        _codeErrorEmail.value = ErrorType.ERROR_OK
    }
    fun restcodeErrorPassword(){
        _codeErrorPassword.value = ErrorType.ERROR_OK
    }
    fun restcodeError(){
        _codeError.value = ErrorType.ERROR_OK
    }
    fun onChangedUser(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isEnabledLogin.value = isValidUserLogin()
    }
    private fun isValidUserLogin():Boolean{
        return _email.value?.isNotBlank() == true && Patterns.EMAIL_ADDRESS.matcher(_email.value!!).matches() &&
                _password.value?.isNotBlank() == true && _password.value?.length!! >= 8
    }

    fun login(isLoading:(Boolean) -> Unit,doLogin:(AuthData) -> Unit) {
        isLoading(true)
        //Esto debo descomentarlo
        viewModelScope.launch {
            try{
                val auth = loginUseCase(_email.value?:"", _password.value?:"")
                if (auth == null) throw Exception("Usuario y/o contraseña incorrecto")
                doLogin(auth)
            }
            catch(e:Exception){
                _message.value = e.message?: "Error al registrar el usuario"
                _showMessageDialog.value = true
            }
            finally {
                isLoading(false)
            }
        }
        //navController.navigate(Screen.Dashboard.route)
    }

    fun hiddenMessageDialog(){
        _showMessageDialog.value = false
    }

    fun checkEmail() {
        if(_email.value.isNullOrEmpty()){
            _codeErrorEmail.value = ErrorType.ERROR_REQUERID
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(_email.value?:"").matches())
            _codeErrorEmail.value = ErrorType.ERROR_EMAIL_FORMAT
    }

    fun checkPassword() {
        if(_password.value.isNullOrEmpty()){
            _codeErrorPassword.value = ErrorType.ERROR_REQUERID
            return
        }
        if(_password.value?.length!! < 8)
            _codeErrorPassword.value = ErrorType.ERROR_PASSWORD_LENGTH
    }

}