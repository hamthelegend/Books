package com.thebrownfoxx.books.ui.screens.archivedbooks

import com.thebrownfoxx.books.model.Book

sealed class ArchivedBooksEvent {
    data class Delete(val book: Book): ArchivedBooksEvent()
    data object UnarchiveAll: ArchivedBooksEvent()
    data object DeleteAll: ArchivedBooksEvent()
}
