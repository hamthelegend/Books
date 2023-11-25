package com.thebrownfoxx.books.ui.screens.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.books.model.BookType
import com.thebrownfoxx.books.realm.BookRealmDatabase
import com.thebrownfoxx.books.ui.extensions.updated
import com.thebrownfoxx.books.ui.screens.navArgs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _newPagesRead = MutableStateFlow<Int?>(null)
    val newPagesRead = _newPagesRead.asStateFlow()

    val savePagesReadButtonVisible =
        newPagesRead.mapToStateFlow(scope = viewModelScope) { it != null }

    private val _deleteDialogVisible = MutableStateFlow(false)
    val deleteDialogVisible = _deleteDialogVisible.asStateFlow()

    fun updateNewPagesRead(newPagesRead: String) {
        _newPagesRead.update { oldPagesRead ->
            val newPagesReadInt = oldPagesRead.updated(newPagesRead)
            if ((newPagesReadInt ?: 0) > (book.value?.pages ?: 0)) oldPagesRead else newPagesReadInt
        }
    }

    fun savePagesRead() {
        viewModelScope.launch {
            database.updateBookProgress(
                id = org.mongodb.kbson.ObjectId(bookId),
                pagesRead = newPagesRead.value ?: 0,
            )
            _newPagesRead.update { null }
        }
    }

    fun favorite() {
        viewModelScope.launch {
            if (book.value?.type == BookType.NonFavorite) {
                database.favoriteBook(org.mongodb.kbson.ObjectId(bookId))
            }
        }
    }

    fun unfavorite() {
        viewModelScope.launch {
            if (book.value?.type == BookType.Favorite) {
                database.unfavoriteBook(org.mongodb.kbson.ObjectId(bookId))
            }
        }
    }

    fun archive() {
        viewModelScope.launch {
            if (book.value?.type == BookType.NonFavorite) {
                database.archiveBook(org.mongodb.kbson.ObjectId(bookId))
                _navigateUp.emit(Unit)
            }
        }
    }

    fun unarchive() {
        viewModelScope.launch {
            if (book.value?.type == BookType.Archived) {
                database.unarchiveBook(org.mongodb.kbson.ObjectId(bookId))
            }
        }
    }

    fun updateDeleteDialogVisible(deleteDialogVisible: Boolean) {
        _deleteDialogVisible.update { deleteDialogVisible }
    }

    fun delete() {
        viewModelScope.launch {
            if (book.value?.type == BookType.Archived) {
                database.deleteBook(org.mongodb.kbson.ObjectId(bookId))
                _navigateUp.emit(Unit)
            }
        }
    }
}
