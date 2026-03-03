package com.gabsee.dicodingevents.di

import android.content.Context
import com.gabsee.dicodingevents.data.local.room.EventDatabase
import com.gabsee.dicodingevents.data.remote.retrofit.ApiConfig
import com.gabsee.dicodingevents.preferences.SettingPreferences
import com.gabsee.dicodingevents.preferences.dataStore
import com.gabsee.dicodingevents.data.repository.EventRepository
import com.gabsee.dicodingevents.data.repository.SettingRepository

object Injection {
    fun provideSettingRepository(context: Context): SettingRepository {
        val dataStore = context.dataStore
        val pref = SettingPreferences.getInstance(dataStore)
        return SettingRepository.getInstance(pref)
    }
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(
            apiService,
            dao
        )
    }
}