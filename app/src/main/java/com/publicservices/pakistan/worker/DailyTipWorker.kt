package com.publicservices.pakistan.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.publicservices.pakistan.data.DailyTips
import com.publicservices.pakistan.utils.NotificationHelper
import java.util.Calendar

class DailyTipWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        return try {
            val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            val language = inputData.getString("language") ?: "en"
            
            val tip = DailyTips.getTipForDay(dayOfYear, language)
            
            val title = if (language == "ur") tip.titleUr else tip.titleEn
            val message = if (language == "ur") tip.messageUr else tip.messageEn
            
            NotificationHelper.showNotification(
                context = applicationContext,
                title = title,
                message = message,
                serviceId = tip.serviceId
            )
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
