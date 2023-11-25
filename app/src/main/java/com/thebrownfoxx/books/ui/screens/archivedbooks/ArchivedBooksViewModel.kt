package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.realm.BookRealmDatabase
import com.thebrownfoxx.books.ui.components.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchivedBooksViewModel(private val database: BookRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val books = combineToStateFlow(
        database.getAllArchivedBooks(),
        searchQuery,
        scope = viewModelScope,
        initialValue = null,
    ) { realmBooks, searchQuery ->
        realmBooks.map { realmBook ->
            realmBook.toBook()
        }.search(searchQuery) { it.title }
    }

    private val _deleteDialogState =
        MutableStateFlow<DialogState<Book>>(DialogState.Hidden())
    val deleteDialogState = _deleteDialogState.asStateFlow()


    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    suspend fun initiateDelete(book: Book): Boolean {
        _deleteDialogState.update { DialogState.Pending(book) }
        return withContext(Dispatchers.Default) {
            var state: DialogState<Book>
            while (true) {
                state = deleteDialogState.value
                if (state is DialogState.Canceled || state is DialogState.Confirmed){
                    _deleteDialogState.update { DialogState.Hidden() }
                    break
                }
            }
            return@withContext state is DialogState.Confirmed
        }
    }

    fun cancelDelete() {
        _deleteDialogState.update { DialogState.Canceled() }
    }

    fun delete() {
        val state = deleteDialogState.value
        if (state is DialogState.Pending) {
            viewModelScope.launch {
                database.deleteBook(id = org.mongodb.kbson.ObjectId(state.value.id))
            }
        }
        _deleteDialogState.update { DialogState.Confirmed() }
    }
}