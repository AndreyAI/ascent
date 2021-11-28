package com.example.diplomstrava.presentation.addactivity

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.ActivityContract
import com.example.diplomstrava.networking.StravaApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.threeten.bp.Instant
import timber.log.Timber


@HiltWorker
class PostActivityWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val api: StravaApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        Timber.d("work started ${Thread.currentThread().name}")

        api.postActivity(
            Activity(
                id = 0L,
                name = inputData.getString(ActivityContract.Columns.NAME)!!,
                type = inputData.getString(ActivityContract.Columns.TYPE)!!,
                date = inputData.getString(ActivityContract.Columns.DATE)!!,
                time = inputData.getLong(ActivityContract.Columns.TIME, 0),
                distance = inputData.getDouble(ActivityContract.Columns.DISTANCE, 0.0),
                elevation = inputData.getLong(ActivityContract.Columns.ELEVATION, 0),
                description = inputData.getString(ActivityContract.Columns.DESCRIPTION)!!
            )
        ).execute()

        return Result.success()

//        return when (1) {
//            "1" -> Result.retry()
//            "2" -> Result.failure()
//            else -> Result.success()
//        }
    }

}