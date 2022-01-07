package com.example.diplomstrava.presentation.main

import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.diplomstrava.R
import com.example.diplomstrava.data.LastActivityFromApp
import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.presentation.NotificationChanel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class DailyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val activityDao: ActivityDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val lastActivity = activityDao.getLastActivityFromApp()
        if(lastActivity.isNullOrEmpty()){
            activityDao.insertLastActivityFromApp(
                LastActivityFromApp(id = 1L, Calendar.getInstance().timeInMillis)
            )
            return Result.success()
        }

        val currentDate = Calendar.getInstance()

        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, 12)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        var timeDiff = dueDate.timeInMillis - lastActivity.first().timeStamp
        if (timeDiff > MILLIS_IN_DAY) {
            showNotification()
            Timber.d("HAHAHAHAHAHA")
        }

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        timeDiff =
            dueDate.timeInMillis - currentDate.timeInMillis // between setpoint and now (calculate offset delay time)

        val dailyWorkRequest = OneTimeWorkRequestBuilder<DailyWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(MainActivity.NOTIFY_WORK_ID)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(
                dailyWorkRequest
            )
        return Result.success()
    }

    private fun showNotification() {

        val notification =
            NotificationCompat.Builder(applicationContext, NotificationChanel.CHANNEL_ID)
                .setContentTitle(applicationContext.getString(R.string.training_time))
                .setContentText(applicationContext.getString(R.string.notify_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(100, 200, 500, 500))
                .setSmallIcon(R.drawable.ic_alarm)
                //.setLargeIcon(largeIcon)
                .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(NOTIFICATION_ID, notification)

    }

    companion object {
        private const val MILLIS_IN_DAY = 86400000
        private const val NOTIFICATION_ID = 12345
    }
}