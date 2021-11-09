//package com.example.diplomstrava.presentation.activities.adapter
//
//import org.threeten.bp.*
//import org.threeten.bp.LocalDateTime.ofInstant
//import org.threeten.bp.OffsetDateTime.ofInstant
//import org.threeten.bp.OffsetTime.ofInstant
//import org.threeten.bp.format.DateTimeFormatter
//import org.threeten.bp.format.FormatStyle
//
//
//fun main() {
//
//    val dataText = "2017-11-14T02:30:05Z"
//    val instant = Instant.parse(dataText)
//
//    val a = ZoneOffset.UTC
//
//    val localDateTime = LocalDateTime.ofInstant(instant, (ZoneOffset.UTC as ZoneId)) //change zoneId
//
//    //val formatted = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(localDateTime)//ofPattern("MM-dd hh:mm").format(localDateTime)
//
//    val formatted = DateTimeFormatter.ofPattern("dd MMMM yyyy в hh:mm").format(localDateTime)
//
//    println(formatted)
//
//
//    //print(localDateTime)
//
//}

