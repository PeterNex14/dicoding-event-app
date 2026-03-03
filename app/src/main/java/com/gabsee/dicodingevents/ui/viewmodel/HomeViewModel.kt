package com.gabsee.dicodingevents.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.gabsee.dicodingevents.data.Result
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.data.repository.EventRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _syncStatus = MutableLiveData<Result<Unit>>()
    val syncStatus: LiveData<Result<Unit>> = _syncStatus

    private val _searchQuery = MutableLiveData("")

    val upcomingCount: LiveData<Int> = eventRepository.getUpcomingCount()
    val finishedCount: LiveData<Int> = eventRepository.getFinishedCount()

    val limitedUpcomingEvent: LiveData<List<EventEntity>> = eventRepository.getUpcomingEvents().map {
        it.take(5)
    }

    val allEvents: LiveData<List<EventEntity>> = _searchQuery.switchMap { query ->
        if (query.isNullOrBlank()) {
            eventRepository.getAllEvents()
        } else {
            eventRepository.searchEvents(query)
        }
    }

    init {
        viewModelScope.launch {
            if (eventRepository.isDatabaseEmpty()) {
                refreshData()
            }
        }
    }

    fun toggleBookmark(event: EventEntity) {
        viewModelScope.launch {
            val newStatus = !event.isBookmarked
            eventRepository.setBookmark(event.id, newStatus)
        }
    }

    fun setSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun refreshData() {
        viewModelScope.launch {
            _syncStatus.value = Result.Loading
            try {
                eventRepository.syncEvents()
                _syncStatus.value = Result.Success(Unit)
            } catch (e: Exception) {
                _syncStatus.value = Result.Error(e.message.toString())
            }
        }
    }

}