package com.publicservices.pakistan.utils

import android.content.Context
import androidx.work.*
import com.publicservices.pakistan.worker.DailyTipWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationScheduler {
    private const val WORK_NAME = "daily_tip_notification"
    
    /** Schedules periodic daily tips via WorkManager (no foreground service). Only call when user has opted in; aligns with Play policy on non-disruptive messaging. */
    fun scheduleDailyNotifications(context: Context, language: String) {
        val workManager = WorkManager.getInstance(context)
        
        // Cancel existing work
        workManager.cancelUniqueWork(WORK_NAME)
        
        // Calculate initial delay to next 9 AM
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        
        val delayHours = if (currentHour < 9) {
            // If before 9 AM, schedule for today at 9 AM
            (9 - currentHour).toLong()
        } else {
            // If after 9 AM, schedule for tomorrow at 9 AM
            (24 - currentHour + 9).toLong()
        }
        
        val inputData = Data.Builder()
            .putString("language", language)
            .build()
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .setRequiresCharging(false)
            .build()
        
        val dailyWork = PeriodicWorkRequestBuilder<DailyTipWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(delayHours, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInputData(inputData)
            .addTag("daily_tips")
            .build()
        
        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            dailyWork
        )
    }
    
    fun cancelNotifications(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}
