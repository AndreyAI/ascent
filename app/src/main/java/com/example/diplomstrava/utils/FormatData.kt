package com.example.diplomstrava.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat

object FormatData {

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

            return if (dateActivity.equals(dateToday))
                "Сгодня в ${DateTimeFormatter.ofPattern("HH:mm").format(localDateTime)}"
            else
                DateTimeFormatter.ofPattern("dd MMMM yyyy в HH:mm").format(localDateTime)
        } catch (e: Exception) {
            return ""
        }

    }

}