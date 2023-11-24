package com.thebrownfoxx.books.ui.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatted(): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    return format(formatter)
}