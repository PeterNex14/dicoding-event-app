package com.gabsee.dicodingevents.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gabsee.dicodingevents.di.Injection

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val repository = Injection.provideRepository(applicationContext)
        return try {
            repository.syncEvents()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}