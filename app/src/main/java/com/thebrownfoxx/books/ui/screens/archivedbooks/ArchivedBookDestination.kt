package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.ui.screens.book.BookDestination
import com.thebrownfoxx.books.ui.screens.book.BookNavArgs
import com.thebrownfoxx.books.ui.screens.destinations.ArchivedEditBookDestination
import com.thebrownfoxx.books.ui.screens.navhost.ArchiveNavGraph

@ArchiveNavGraph
@Destination(navArgsDelegate = BookNavArgs::class)
@Composable
fun ArchivedBook(
    navigator: DestinationsNavigator,
    showSnackbarMessage: (String) -> Unit,
) {
    BookDestination(
        onNavigateUp = { navigator.navigateUp() },
        onEdit = { book -> navigator.navigate(ArchivedEditBookDestination(bookId = book.id)) },
        showSnackbarMessage = showSnackbarMessage,
    )
}