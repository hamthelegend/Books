package com.thebrownfoxx.books.ui.extensions

fun Int?.updated(newValue: String) = when (newValue) {
    "" -> null
    else -> newValue.toIntOrNull() ?: this
}