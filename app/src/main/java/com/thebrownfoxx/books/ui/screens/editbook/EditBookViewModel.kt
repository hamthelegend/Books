package com.thebrownfoxx.books.ui.screens.editbook

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.books.realm.BookRealmDatabase
import com.thebrownfoxx.books.ui.extensions.updated
import com.thebrownfoxx.books.ui.screens.navArgs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class EditBookViewModel(
    private val database: BookRealmDatabase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val bookId = savedStateHandle.navArgs<EditBookNavArgs>().bookId
    val book = database.getBook(org.mongodb.kbson.ObjectId(bookId))
        .mapToStateFlow(scope = viewModelScope, initialValue = null) { realmBook ->
            realmBook?.toBook()
        }

    private val _navigateUp = MutableSharedFlow<Unit>()
    val navigateUp = _navigateUp.asSharedFlow()

    private val _state = MutableStateFlow(EditBookState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            book.collect { book ->
                if (book != null) _state.update { EditBookState(book) }
            }
        }
    }

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
            it.copy(
                pages = previousPages.updated(pages),
                showPagesRequired = false,
            )
        }
    }

    fun saveChanges() {
        var state = state.value
        if (state.title.isBlank()) state = state.copy(showTitleRequired = true)
        if (state.author.isBlank()) state = state.copy(showAuthorRequired = true)
        if (state.datePublished == null) state = state.copy(showDatePublishedRequired = true)
        if (state.pages == null) state = state.copy(showPagesRequired = true)

        if (!state.hasErrors) {
            viewModelScope.launch {
                database.updateBook(
                    id = org.mongodb.kbson.ObjectId(bookId),
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