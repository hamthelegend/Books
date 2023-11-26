package com.thebrownfoxx.books.ui.screens.nonfavoritebooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.realm.BookRealmDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NonFavoriteBooksViewModel(private val database: BookRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _bookArchived = MutableSharedFlow<Book>()
    val bookArchived = _bookArchived.asSharedFlow()

    val books = combineToStateFlow(
        database.getAllNonFavoriteBooks(),
        searchQuery,
        scope = viewModelScope,
        initialValue = null,
    ) { realmBooks, searchQuery ->
        realmBooks.map { realmBook ->
            realmBook.toBook()
        }.search(searchQuery) { it.title }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun archive(book: Book) {
        viewModelScope.launch {
            database.archiveBook(id = org.mongodb.kbson.ObjectId(book.id))
            _bookArchived.emit(book)
        }
    }
}