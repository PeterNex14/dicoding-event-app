package com.gabsee.dicodingevents.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "mediaCover")
    val mediaCover: String,

    @ColumnInfo(name = "imageLogo")
    val imageLogo: String,

    @ColumnInfo(name = "registrants")
    val registrants: Int,

    @ColumnInfo(name = "quota")
    val quota: Int,

    @ColumnInfo(name = "link")
    val link: String,

    @ColumnInfo(name = "organizer")
    val organizer: String,

    @ColumnInfo(name = "beginTime")
    val beginTime: String,

    @ColumnInfo(name = "endTime")
    val endTime: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "cityName")
    val cityName: String,

    @ColumnInfo(name = "bookmarked")
    val isBookmarked: Boolean



)