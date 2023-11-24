package com.thebrownfoxx.books.ui.screens.nonfavoritebooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.application
import com.thebrownfoxx.books.ui.components.DialogStateChangeListener
import com.thebrownfoxx.books.ui.screens.destinations.AddBookDestination
import com.thebrownfoxx.books.ui.screens.navhost.BooksNavGraph

@BooksNavGraph(start = true)
@Destination
@Composable
fun NonFavoriteBooks(navigator: DestinationsNavigator) {
    val viewModel = viewModel {
        NonFavoriteBooksViewModel(application.database)
    }
    
    with(viewModel) {
        val books by books.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val archiveDialogState by archiveDialogState.collectAsStateWithLifecycle()
        
        NonFavoriteBooksScreen(
            books = books,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            onAddBook = { navigator.navigate(AddBookDestination) },
            onOpenBook = { },
            archiveDialogState = archiveDialogState,
            archiveDialogStateChangeListener = DialogStateChangeListener(
                onInitiateAction = ::initiateArchive,
                onCancelAction = ::cancelArchive,
                onCommitAction = ::archive,
            ),
        )
    }
}