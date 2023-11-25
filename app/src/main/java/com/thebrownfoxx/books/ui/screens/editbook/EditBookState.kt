package com.thebrownfoxx.books.ui.screens.editbook

import com.thebrownfoxx.books.model.Book
import java.time.LocalDate

data class EditBookState(
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

fun EditBookState(book: Book) = EditBookState(
    title = book.title,
    author = book.author,
    datePublished = book.datePublished,
    pages = book.pages,
)

class EditBookStateChangeListener(
    val onTitleChange: (String) -> Unit,
    val onAuthorChange: (String) -> Unit,
    val onDatePublishedPickerVisibleChange: (Boolean) -> Unit,
    val onDatePublishedChange: (LocalDate) -> Unit,
    val onPagesChange: (String) -> Unit,
    val onSaveChanges: () -> Unit,
) {
    companion object {
        fun empty() = EditBookStateChangeListener(
            onTitleChange = {},
            onAuthorChange = {},
            onDatePublishedPickerVisibleChange = {},
            onDatePublishedChange = {},
            onPagesChange = {},
            onSaveChanges = {},
        )
    }
}