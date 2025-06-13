package com.mindera.base.utils

import java.text.DateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Long?.getFormattedDate(): String {
    if (this == null) return "N/A"
    val date = Date(this * 1000)
    val dateFormatter = DateFormat.getDateInstance(
        DateFormat.MEDIUM,
        Locale.getDefault()
    )
    return dateFormatter.format(date)
}

fun Long?.getFormattedTime(): String {
    if (this == null) return "N/A"
    val date = Date(this * 1000)
    val timeFormatter = DateFormat.getTimeInstance(
        DateFormat.SHORT,
        Locale.getDefault()
    )
    return timeFormatter.format(date)
}

fun Long?.isInFuture(): Boolean {
    if (this == null) return true
    val now = System.currentTimeMillis() / 1000
    return this > now
}

fun Long?.getDaysDifference(): Long {
    if (this == null) return 0
    val now = System.currentTimeMillis() / 1000
    val diffInSeconds = abs(this - now)
    return TimeUnit.SECONDS.toDays(diffInSeconds)
}

fun Long?.getYear(): Int {
    if (this == null) return 0
    return Instant.ofEpochSecond(this)
        .atZone(ZoneId.systemDefault())
        .year

}

