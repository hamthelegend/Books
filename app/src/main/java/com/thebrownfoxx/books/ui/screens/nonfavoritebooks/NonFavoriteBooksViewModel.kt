package com.thebrownfoxx.books.ui.screens.nonfavoritebooks

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

class NonFavoriteBooksViewModel(private val database: BookRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

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

    private val _archiveDialogState =
        MutableStateFlow<DialogState<Book>>(DialogState.Hidden())
    val archiveDialogState = _archiveDialogState.asStateFlow()


    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    suspend fun initiateArchive(book: Book): Boolean {
        _archiveDialogState.update { DialogState.Pending(book) }
        return withContext(Dispatchers.Default) {
            var state: DialogState<Book>
            while (true) {
                state = archiveDialogState.value
                if (state is DialogState.Canceled || state is DialogState.Confirmed){
                    _archiveDialogState.update { DialogState.Hidden() }
                    break
                }
            }
            return@withContext state is DialogState.Confirmed
        }
    }

    fun cancelArchive() {
        _archiveDialogState.update { DialogState.Canceled() }
    }

    fun archive() {
        val state = archiveDialogState.value
        if (state is DialogState.Pending) {
            viewModelScope.launch {
                database.archiveBook(id = org.mongodb.kbson.ObjectId(state.value.id))
            }
        }
        _archiveDialogState.update { DialogState.Confirmed() }
    }
}