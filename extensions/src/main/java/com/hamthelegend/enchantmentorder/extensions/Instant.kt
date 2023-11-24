package com.hamthelegend.enchantmentorder.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun Instant.toLocalDateTime(): LocalDateTime {
    val zoneId = ZoneId.systemDefault()
    return atZone(zoneId).toLocalDateTime()
}

fun Instant.toLocalDate(): LocalDate {
    val zoneId = ZoneId.systemDefault()
    return atZone(zoneId).toLocalDate()
}

fun Instant.toLocalTime(): LocalTime {
    val zoneId = ZoneId.systemDefault()
    return atZone(zoneId).toLocalTime()
}