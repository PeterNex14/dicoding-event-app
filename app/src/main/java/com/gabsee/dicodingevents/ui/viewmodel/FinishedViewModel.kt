package com.gabsee.dicodingevents.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabsee.dicodingevents.data.Result
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.repository.EventRepository
import kotlinx.coroutines.launch

class FinishedViewModel(private val repository: EventRepository) : ViewModel() {
    private val _syncStatus = MutableLiveData<Result<Unit>>()
    val syncStatus: LiveData<Result<Unit>> = _syncStatus

    val finishedEvents = repository.getFinishedEvents()

    fun toggleBookmark(event: EventEntity) {
        viewModelScope.launch {
            val newStatus = !event.isBookmarked
            repository.setBookmark(event.id, newStatus)
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _syncStatus.value = Result.Loading
            try {
                repository.syncEvents()
                _syncStatus.value = Result.Success(Unit)
            } catch (e: Exception) {
                _syncStatus.value = Result.Error(e.message.toString())
            }
        }
    }

}