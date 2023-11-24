package com.thebrownfoxx.books.ui.screens.addbook

import java.time.LocalDate

data class AddBookState(
    val title: String = "",
    val author: String = "",
    val datePublishedPickerVisible: Boolean = false,
    val datePublished: LocalDate? = null,
    val pages: Int? = null,
    val showTitleRequired: Boolean = false,
    val showAuthorRequired: Boolean = false,
    val showDatePublishedRequired: Boolean = false,
    val showPagesRequired: Boolean = false,
) {
    val hasErrors = showTitleRequired || showAuthorRequired || showPagesRequired
}

class AddBookStateChangeListener(
    val onTitleChange: (String) -> Unit,
    val onAuthorChange: (String) -> Unit,
    val onDatePublishedPickerVisibleChange: (Boolean) -> Unit,
    val onDatePublishedChange: (LocalDate) -> Unit,
    val onPagesChange: (String) -> Unit,
    val onAddBook: () -> Unit,
) {
    companion object {
        fun empty() = AddBookStateChangeListener(
            onTitleChange = {},
            onAuthorChange = {},
            onDatePublishedPickerVisibleChange = {},
            onDatePublishedChange = {},
            onPagesChange = {},
            onAddBook = {},
        )
    }
}