package com.alebrije_estudios.metabolique.recobery_account.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecoveryAccountViewModel @Inject constructor():ViewModel() {
    private val _email:MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String> = _email

    private val _isEnabled:MutableLiveData<Boolean> = MutableLiveData()
    val isEnabled: LiveData<Boolean> = _isEnabled

    fun onValidatedEmail(email: String) {
        _email.value = email
        _isEnabled.value = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
