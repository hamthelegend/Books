package com.thebrownfoxx.books.ui.screens.favoritebooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.application
import com.thebrownfoxx.books.ui.screens.destinations.FavoriteBookDestination
import com.thebrownfoxx.books.ui.screens.navhost.FavoritesNavGraph

@FavoritesNavGraph(start = true)
@Destination
@Composable
fun FavoriteBooks(navigator: DestinationsNavigator) {
    val viewModel = viewModel {
        FavoriteBooksViewModel(application.database)
    }
    
    with(viewModel) {
        val books by books.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        
        FavoriteBooksScreen(
            books = books,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            onOpenBook = { navigator.navigate(FavoriteBookDestination(bookId = it.id)) },
        )
    }
}