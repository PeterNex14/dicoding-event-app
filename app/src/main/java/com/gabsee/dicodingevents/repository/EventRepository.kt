package com.gabsee.dicodingevents.repository

import androidx.lifecycle.LiveData
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.data.local.room.EventDao
import com.gabsee.dicodingevents.data.remote.retrofit.ApiService
import com.gabsee.dicodingevents.utils.toEventEntity
import kotlin.concurrent.Volatile

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {

    suspend fun syncEvents() {
        val response = apiService.getEvents()
        val remoteEvents = response.listEvents

        val eventEntities = remoteEvents.map {
            val isExistingBookmark = eventDao.isEventBookmarked(it.id)

            it.toEventEntity(isExistingBookmark)
        }

        eventDao.updateData(eventEntities)
    }

    fun getUpcomingEvents() = eventDao.getUpcomingEvents()

    fun getFinishedEvents() = eventDao.getFinishedEvents()
    fun getBookmarkedEvents() = eventDao.getBookmarkedEvents()

    fun getUpcomingCount() = eventDao.getUpcomingCount()

    fun getFinishedCount() = eventDao.getFinishedCount()

    fun getEventById(id: Int): LiveData<EventEntity> {
        return eventDao.getEventById(id)
    }

    fun searchEvents(query: String): LiveData<List<EventEntity>> {
        return eventDao.searchEvents(query)
    }

    fun getAllEvents() = eventDao.getEvents()

    suspend fun isDatabaseEmpty() = eventDao.getCount() == 0

    suspend fun setBookmark(eventId: Int, isBookmarked: Boolean) {
        val status = if (isBookmarked) 1 else 0
        eventDao.updateBookmarkStatus(eventId, status)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }

}