package com.example.diplomstrava.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.ActivityMainBinding
import com.example.diplomstrava.presentation.DailyWorker
import com.example.diplomstrava.presentation.addactivity.AddActivityFragment
import com.example.diplomstrava.presentation.share.ShareListFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ExitDialog.Exit {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.activityMain)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNotify()
    }

    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment)
        if (navHost?.childFragmentManager?.fragments?.get(0) is AddActivityFragment ||
            navHost?.childFragmentManager?.fragments?.get(0) is ShareListFragment
        )
            super.onBackPressed()
        else {
            navHost?.childFragmentManager?.let {
                ExitDialog()
                    .show(it, "Duration")
            }
        }
    }

    /*
            val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PostActivityWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest)
     */

    private fun initNotify() {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
// Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 12)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)  //24
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        Timber.d("dueDif ${timeDiff}")
//
        val dailyWorkRequest = OneTimeWorkRequestBuilder<DailyWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(NOTIFY_WORK_ID)
            .build()

//        WorkManager.getInstance(applicationContext)
//            .enqueueUniqueWork(
//                NOTIFY_WORK_ID,
//                ExistingWorkPolicy.KEEP,
//                dailyWorkRequest
//            )
        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)
    }

    override fun exit() {
        finish()
    }

    companion object {
        const val NOTIFY_WORK_ID = "download work"
    }
}