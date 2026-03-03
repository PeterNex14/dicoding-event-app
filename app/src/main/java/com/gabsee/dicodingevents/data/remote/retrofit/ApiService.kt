package com.gabsee.dicodingevents.data.remote.retrofit


import com.gabsee.dicodingevents.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("q") query: String? = null
    ): EventResponse
}