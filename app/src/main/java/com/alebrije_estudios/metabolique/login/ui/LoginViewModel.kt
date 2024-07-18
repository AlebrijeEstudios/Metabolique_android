package com.alebrije_estudios.metabolique.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password:LiveData<String> = _password
    private val _email: MutableLiveData<String> = MutableLiveData()
    val email:LiveData<String> = _email
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isEnabledLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isEnabledLogin: LiveData<Boolean> = _isEnabledLogin


    fun onChangedUser(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isEnabledLogin.value = isValidUserLogin()
    }
    private fun isValidUserLogin():Boolean{
        return _email.value?.isNotBlank() == true && Patterns.EMAIL_ADDRESS.matcher(_email.value!!).matches() &&
                _password.value?.isNotBlank() == true && _password.value?.length!! >= 8
    }

    fun login() {
        // TODO: Implement login logic here.
    }

}