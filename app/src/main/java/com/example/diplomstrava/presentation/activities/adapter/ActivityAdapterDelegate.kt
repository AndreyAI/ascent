package com.example.diplomstrava.presentation.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.databinding.ItemActivityBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import java.math.RoundingMode
import java.text.DecimalFormat
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception


class ActivityAdapterDelegate(
    private val userName: String,
    private val avatarUrl: String,
    private val onShareActivity: (Activity) -> Unit
) :
    AbsListItemAdapterDelegate<Activity, Activity, ActivityAdapterDelegate.Holder>() {


    override fun isForViewType(
        item: Activity,
        items: MutableList<Activity>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, userName, avatarUrl, onShareActivity)
    }

    override fun onBindViewHolder(item: Activity, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemActivityBinding,
        private val userName: String,
        private val avatarUrl: String,
        private val onShareActivity: (Activity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentActivity: Activity? = null

        init {
            binding.imageShare.setOnClickListener {
                currentActivity?.let {
                    onShareActivity
                }
            }
        }

        fun bind(activity: Activity) {
            currentActivity = activity
            binding.root.setOnClickListener {
                currentActivity?.let {
                    onShareActivity(it)
                }
            }
            Glide.with(itemView)
                .load(this.avatarUrl)
                .into(binding.imageAvatar)
            binding.textName.text = userName
            binding.textDate.text = formatDate(activity.date)
            binding.textTitleActivity.text = activity.name
            binding.textDistanceField.text = formatKm(activity.distance)
            binding.textTimeField.text = formatTime(activity.time)
            binding.textElevationField.text = formatM(activity.elevation) // or foot?
            binding.textActivityGroup.text = activity.type
            if (activity.description == null) binding.textDescription.isVisible = false
            else binding.textDescription.text = activity.description

        }

    }

    companion object {

        fun formatKm(meters: Double): String {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.FLOOR
            val km = df.format(meters / 1000.0)
            return "$km km"
        }

        fun formatM(meters: Long): String {
            return "$meters m"
        }

        fun formatTime(seconds: Long): String {
            var time = ""
            val hours = seconds.toInt() / 3600
            if (hours != 0) time += "$hours h"
            var remainder = seconds.toInt() - hours * 3600
            val minutes = remainder / 60
            if (minutes != 0) time += "$minutes m"
            remainder -= minutes * 60
            val secs = remainder
            if (secs != 0) time += "$secs s"

            if (time == "") time = "0 s"

            return time
        }

        fun formatDate(dateTime: String): String {

            try {
//                val formatted: String

                val instant = Instant.parse(dateTime)
                val instantToday = Instant.now()


                val localDateTime =
                    LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
                val localDateTimeToday =
                    LocalDateTime.ofInstant(instantToday, ZoneOffset.UTC)

                val dateActivity =
                    DateTimeFormatter.ofPattern("dd MMMM yyyy").format(localDateTime)
                val dateToday =
                    DateTimeFormatter.ofPattern("dd MMMM yyyy").format(localDateTimeToday)

                //val formatted = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(localDateTime)//ofPattern("MM-dd hh:mm").format(localDateTime)

                return if (dateActivity.equals(dateToday))
                    "Сгодня в ${DateTimeFormatter.ofPattern("HH:mm").format(localDateTime)}"
                else
                    DateTimeFormatter.ofPattern("dd MMMM yyyy в HH:mm").format(localDateTime)
            } catch (e: Exception) {
                return ""
            }

        }


    }

}