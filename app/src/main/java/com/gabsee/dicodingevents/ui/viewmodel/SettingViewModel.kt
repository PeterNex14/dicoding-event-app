package com.gabsee.dicodingevents.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gabsee.dicodingevents.repository.SettingRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: SettingRepository): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return repository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getReminderSettings(): LiveData<Boolean> = repository.getReminderSetting().asLiveData()

    fun saveReminderSettings(isReminderActive: Boolean) {
        viewModelScope.launch {
            repository.saveReminderSetting(isReminderActive)
        }
    }
}