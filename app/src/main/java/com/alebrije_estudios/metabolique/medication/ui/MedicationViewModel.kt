package com.alebrije_estudios.metabolique.medication.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alebrije_estudios.metabolique.login.data.network.model.AuthData
import com.alebrije_estudios.metabolique.medication.domain.AddMedicationUseCase
import com.alebrije_estudios.metabolique.medication.domain.DeleteMedicationUseCase
import com.alebrije_estudios.metabolique.medication.domain.GetMedicationsUseCase
import com.alebrije_estudios.metabolique.medication.domain.UpdateMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val getAllMedicationsUseCase: GetMedicationsUseCase,
    private val addMedicationUseCase: AddMedicationUseCase,
    private val updateMedicationUseCase: UpdateMedicationUseCase,
    private val deleteMedicationUseCase: DeleteMedicationUseCase,
    //private val getMedicationAftereffectsUseCase: GetMedicationAftereffectsUseCase,
) : ViewModel() {
    private val _showAddMedication: MutableLiveData<Boolean> = MutableLiveData()
    val showAddMedication: LiveData<Boolean> = _showAddMedication
    private val _medication: MutableLiveData<MedicationModel> = MutableLiveData()
    val medication: LiveData<MedicationModel> = _medication
    private val _showRegisterAftereffect: MutableLiveData<Boolean> = MutableLiveData()
    val showRegisterAftereffect: LiveData<Boolean> = _showRegisterAftereffect
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        val aux = MutableLiveData<Boolean>()
        aux.value = false
        aux
    }
    val isLoading: LiveData<Boolean> = _isLoading
    private val _showMessageDelete: MutableLiveData<Boolean> = MutableLiveData()
    val showMessageDelete: LiveData<Boolean> = _showMessageDelete
    private val _localDate: MutableLiveData<LocalDate> by lazy {
        val aux = MutableLiveData<LocalDate>()
        aux.value = LocalDate.now()
        aux
    }
    val localDate: LiveData<LocalDate> = _localDate
    private val _listMedications = mutableStateListOf<MedicationModel>()
    val listMedications: List<MedicationModel> = _listMedications
    fun getListMedications(authData: AuthData) {
        if (_isLoading.value == false && _listMedications.isEmpty()) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    //Getting the list of all
                    val data = getAllMedicationsUseCase(authData, _localDate.value!!)
                    _listMedications.addAll(data["Medications"]!! as List<MedicationModel>)
                    _isLoading.value = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun showAddMedicationScreen() {
        _showAddMedication.value = true
    }

    fun closeAddMedicationScreen() {
        _showAddMedication.value = false
    }

    fun showRegisterAftereffectScreen() {
        _showRegisterAftereffect.value = true
    }

    fun closeRegisterAftereffectScreen() {
        _showRegisterAftereffect.value = false
    }

    fun changeMedication(medication: MedicationModel) {
        _medication.value = medication
    }

    fun changeDate(authData: AuthData,localDate: LocalDate) {
        _localDate.value = localDate
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Simulate async task
                //...
                _listMedications.clear()
                val data = getAllMedicationsUseCase(authData, _localDate.value!!)
                _listMedications.addAll(data["Medications"]!! as List<MedicationModel>)
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteMedication(authData: AuthData, medication: MedicationModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Simulate async task
                //...
                deleteMedicationUseCase(authData, medication.medicationID)
                _listMedications.remove(medication)
                _isLoading.value = false
                _showMessageDelete.value = false
                _showRegisterAftereffect.value =
                    true // After deleting medication, show a success message and allow user to register aftereffect.
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
        fun showDeleteDialog() {
            _showMessageDelete.value = true
        }

        fun hiddenDeleteDialog() {
            _showMessageDelete.value = false
        }

        fun addMedication(authData: AuthData,medication: MedicationModel) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    addMedicationUseCase(authData,_localDate.value!!,medication)
                    _listMedications.add(medication)
                }catch (e: Exception) {
                    e.printStackTrace()
                }
                _isLoading.value = false
            }
        }

        fun updateMedication(authData: AuthData,medication: MedicationModel) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    updateMedicationUseCase(authData,_localDate.value!!,medication)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                _isLoading.value = false
            }
        }
    }