package com.alebrije_estudios.metabolique.register_account.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterAccountViewModel @Inject constructor(): ViewModel() {

    private val _name:MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name
    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String> = _email
    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: LiveData<String> = _password
    private val _confirmPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: LiveData<String> = _confirmPassword
    private val _isEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val isEnabled: LiveData<Boolean> = _isEnabled

    fun onValidated(name: String, email: String, password: String, confirmPassword: String) {
        _name.value = name
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isEnabled.value = isValidRegisterAccount()
    }

    private fun isValidRegisterAccount(): Boolean {
        return _name.value?.isNotBlank() == true && Patterns.EMAIL_ADDRESS.matcher(_email.value!!).matches() &&
            _password.value?.length!! >= 8 && _password.value == _confirmPassword.value
    }


    fun onSubmit() {
        TODO("Not yet implemented")
    }
}
