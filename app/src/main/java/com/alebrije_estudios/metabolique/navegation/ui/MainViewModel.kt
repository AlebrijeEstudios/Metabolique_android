package com.alebrije_estudios.metabolique.navegation.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class TopBarShow {
    SHOW_ALL,
    SHOW_BACK_BUTTON,
    SHOW_NAR_BAR,
    SHOW_NAR_BAR_TITLE,
    HIDE_ALL
}

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _showBackArrow:MutableLiveData<Boolean> = MutableLiveData()
    val showBackArrow: LiveData<Boolean> = _showBackArrow
    private val _showTopBar:MutableLiveData<Boolean> = MutableLiveData()
    val showTopBar: LiveData<Boolean> = _showTopBar
    private val _showNavbar:MutableLiveData<Boolean> = MutableLiveData()
    val showNavbar: LiveData<Boolean> = _showNavbar
    private val _title:MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title

    fun showViews(topBar: TopBarShow) {
        _showBackArrow.value = false
        _showTopBar.value = false
        _showNavbar.value = false
        when (topBar){
            TopBarShow.SHOW_ALL -> {
                _showBackArrow.value = true
                _showTopBar.value = true
                _showNavbar.value = true
            }
            TopBarShow.SHOW_BACK_BUTTON -> {
                _showBackArrow.value = true
                _showTopBar.value = true
            }
            TopBarShow.SHOW_NAR_BAR -> {
                _showNavbar.value = true
            }
            TopBarShow.SHOW_NAR_BAR_TITLE ->{
                _showNavbar.value = true
                _showTopBar.value = true
            }
            TopBarShow.HIDE_ALL -> {
                _showBackArrow.value = false
                _showTopBar.value = false
                _showNavbar.value = false
            }
        }
    }
    @Composable
    fun SetTitle(@StringRes id:Int){
        _title.value = stringResource(id = id)
    }
}