package com.thebrownfoxx.books.ui.screens.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.books.realm.BookRealmDatabase
import com.thebrownfoxx.books.ui.screens.navArgs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val database: BookRealmDatabase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val bookId = savedStateHandle.navArgs<BookNavArgs>().bookId

    private val _navigateUp = MutableSharedFlow<Unit>()
    val navigateUp = _navigateUp.asSharedFlow()

    val book = database.getBook(org.mongodb.kbson.ObjectId(bookId))
        .mapToStateFlow(
            scope = viewModelScope,
            initialValue = null,
        ) { realmBook -> realmBook?.toBook() }

    fun archive() {
        viewModelScope.launch {
            database.archiveBook(org.mongodb.kbson.ObjectId(bookId))
            _navigateUp.emit(Unit)
        }
    }
}
