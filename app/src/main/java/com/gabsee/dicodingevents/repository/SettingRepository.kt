package com.gabsee.dicodingevents.repository

import com.gabsee.dicodingevents.preferences.SettingPreferences
import kotlinx.coroutines.flow.Flow
import kotlin.concurrent.Volatile

class SettingRepository private constructor(
    private val pref: SettingPreferences
) {
    fun getThemeSetting(): Flow<Boolean> = pref.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }

    fun getReminderSetting(): Flow<Boolean> = pref.getReminderSetting()

    suspend fun saveReminderSetting(isReminderActive: Boolean) =
        pref.saveReminderSetting(isReminderActive)

    companion object {
        @Volatile
        private var INSTANCE: SettingRepository? = null
        fun getInstance(pref: SettingPreferences): SettingRepository =
            INSTANCE ?: synchronized(this) {
                SettingRepository(pref).also { INSTANCE = it }
            }
    }
}