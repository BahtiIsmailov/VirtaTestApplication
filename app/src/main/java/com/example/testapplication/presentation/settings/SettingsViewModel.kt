package com.example.testapplication.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.testapplication.app.AppPrefsKey
import com.example.testapplication.utils.prefs.SharedWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedWorker: SharedWorker
) : ViewModel() {


    fun clickOnToggleHideKw(flag: Boolean) {
        sharedWorker.saveMediate(AppPrefsKey.TOGGLE_HIDE_KW, flag)
    }

    fun clickOnToggleHideDistance(flag: Boolean) {
        sharedWorker.saveMediate(AppPrefsKey.TOGGLE_HIDE_DISTANCE, flag)
    }
}