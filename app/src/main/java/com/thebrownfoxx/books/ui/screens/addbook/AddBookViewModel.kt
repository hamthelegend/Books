package com.thebrownfoxx.books.ui.screens.addbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.books.realm.BookRealmDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddBookViewModel(private val database: BookRealmDatabase) : ViewModel() {
    private val _navigateUp = MutableSharedFlow<Unit>()
    val navigateUp = _navigateUp.asSharedFlow()

    private val _state = MutableStateFlow(AddBookState())
    val state = _state.asStateFlow()

    fun updateTitle(title: String) {
        _state.update { it.copy(title = title, showTitleRequired = false) }
    }

    fun updateAuthor(author: String) {
        _state.update { it.copy(author = author, showAuthorRequired = false) }
    }

    fun updateDatePublishedPickerVisible(datePublishedPickerVisible: Boolean) {
        _state.update { it.copy(datePublishedPickerVisible = datePublishedPickerVisible) }
    }

    fun updateDatePublished(datePublished: LocalDate) {
        _state.update {
            it.copy(
                datePublished = datePublished,
                datePublishedPickerVisible = false,
                showDatePublishedRequired = false,
            )
        }
    }

    fun updatePages(pages: String) {
        _state.update {
            val previousPages = it.pages
            val newPages = when (pages) {
                "" -> null
                else -> pages.toIntOrNull() ?: previousPages
            }
            it.copy(pages = newPages, showPagesRequired = false)
        }
    }

    fun addBook() {
        var state = state.value
        if (state.title.isBlank()) state = state.copy(showTitleRequired = true)
        if (state.author.isBlank()) state = state.copy(showAuthorRequired = true)
        if (state.datePublished == null) state = state.copy(showDatePublishedRequired = true)
        if (state.pages == null) state = state.copy(showPagesRequired = true)

        if (!state.hasErrors) {
            viewModelScope.launch {
                database.addBook(
                    author = state.author,
                    title = state.title,
                    datePublished = state.datePublished!!,
                    pages = state.pages!!,
                )
                _navigateUp.emit(Unit)
            }
        }
        _state.update { state }
    }
}