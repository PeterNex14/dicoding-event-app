package com.gabsee.dicodingevents.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.repository.EventRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: EventRepository) : ViewModel() {
    fun getEventDetail(id: Int) = repository.getEventById(id)

    fun toggleBookmark(event: EventEntity) {
        viewModelScope.launch {
            val newStatus = !event.isBookmarked
            repository.setBookmark(event.id, newStatus)
        }
    }
}