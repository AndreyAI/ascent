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

    fun formatTime(minutes: Long): String {
        var time = ""
        val hours = minutes.toInt() / 60
        if (hours != 0) time += "$hours h"
        val min = minutes.toInt() - hours * 60
        if (min != 0) time += " $min m"

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