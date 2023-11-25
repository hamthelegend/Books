package com.thebrownfoxx.books.ui.screens.favoritebooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.books.realm.BookRealmDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoriteBooksViewModel(database: BookRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val books = combineToStateFlow(
        database.getAllFavoriteBooks(),
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
}