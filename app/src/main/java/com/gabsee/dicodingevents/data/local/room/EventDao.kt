package com.gabsee.dicodingevents.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabsee.dicodingevents.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM event ORDER BY beginTime DESC")
    fun getEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE title LIKE '%' || :query || '%' ORDER BY beginTime DESC")
    fun searchEvents(query: String): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE beginTime > datetime('now', 'localtime') ORDER BY beginTime DESC")
    fun getUpcomingEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE endTime < datetime('now', 'localtime') ORDER BY endTime DESC")
    fun getFinishedEvents(): LiveData<List<EventEntity>>

    @Query("SELECT COUNT(*) FROM event WHERE beginTime > datetime('now', 'localtime') ORDER BY beginTime DESC")
    fun getUpcomingCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM event WHERE endTime < datetime('now', 'localtime') ORDER BY endTime DESC")
    fun getFinishedCount(): LiveData<Int>

    @Query("SELECT * FROM event WHERE bookmarked = 1")
    fun getBookmarkedEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE id = :id")
    fun getEventById(id: Int): LiveData<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("DELETE FROM event")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM event")
    suspend fun getCount(): Int

    @Transaction
    suspend fun updateData(events: List<EventEntity>) {
        deleteAll()
        insertEvents(events)
    }
    @Query("UPDATE event SET bookmarked = :isBookmarked WHERE id = :eventId")
    suspend fun updateBookmarkStatus(eventId: Int, isBookmarked: Int)

    @Query("SELECT EXISTS(SELECT * FROM event WHERE id = :id AND bookmarked = 1)")
    suspend fun isEventBookmarked(id: Int): Boolean

}