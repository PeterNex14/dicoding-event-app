package com.gabsee.dicodingevents.utils

import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.data.remote.response.ListEventsItem

fun ListEventsItem.toEventEntity(isBookmarked: Boolean): EventEntity =
    EventEntity(
        id = this.id,
        title = this.name,
        description = this.description,
        summary = this.summary,
        mediaCover = this.mediaCover,
        imageLogo = this.imageLogo,
        registrants = this.registrants,
        quota = this.quota,
        link = this.link,
        organizer = this.ownerName,
        beginTime = this.beginTime,
        endTime = this.endTime,
        category = this.category,
        cityName = this.cityName,
        isBookmarked = isBookmarked
    )