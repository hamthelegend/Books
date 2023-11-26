package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.realm.BookRealmDatabase
import com.thebrownfoxx.books.ui.components.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchivedBooksViewModel(private val database: BookRealmDatabase) : ViewModel() {
    private val _archivedBooksEvent = MutableSharedFlow<ArchivedBooksEvent>()
    val archivedBooksEvent = _archivedBooksEvent.asSharedFlow()

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
    
    private val _unarchiveAllDialogVisible = MutableStateFlow(false)
    val unarchiveAllDialogVisible = _unarchiveAllDialogVisible.asStateFlow()
    
    private val _deleteAllDialogVisible = MutableStateFlow(false)
    val deleteAllDialogVisible = _deleteAllDialogVisible.asStateFlow()
    
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
                _archivedBooksEvent.emit(ArchivedBooksEvent.Delete(state.value))
            }
        }
        _deleteDialogState.update { DialogState.Confirmed() }
    }
    
    fun updateUnarchiveAllDialogVisible(unarchiveDialogVisible: Boolean) {
        _unarchiveAllDialogVisible.update { unarchiveDialogVisible }
    }

    fun unarchiveAll() {
        viewModelScope.launch {
            database.unarchiveAll()
            _unarchiveAllDialogVisible.update { false }
            _archivedBooksEvent.emit(ArchivedBooksEvent.UnarchiveAll)
        }
    }

    fun updateDeleteAllDialogVisible(deleteDialogVisible: Boolean) {
        _deleteAllDialogVisible.update { deleteDialogVisible }
    }

    fun deleteAll() {
        viewModelScope.launch {
            database.deleteAllArchived()
            _deleteAllDialogVisible.update { false }
            _archivedBooksEvent.emit(ArchivedBooksEvent.DeleteAll)
        }
    }
}