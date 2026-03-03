package com.gabsee.dicodingevents.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gabsee.dicodingevents.R
import com.gabsee.dicodingevents.data.remote.retrofit.ApiConfig

class DailyReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val apiService = ApiConfig.getApiService()
            val response = apiService.getEvents(-1, 1)
            val eventList = response.listEvents

            if (eventList.isNotEmpty()) {
                val event = eventList[0]
                val title = event.name
                val time = event.beginTime
                showNotification(title, time)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification(eventName: String, eventTime: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Event Terdekat Hari Ini")
            .setContentText("$eventName pada $eventTime")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)

    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "daily_reminder_channel"
        const val CHANNEL_NAME = "Event Daily Reminder"
    }
}