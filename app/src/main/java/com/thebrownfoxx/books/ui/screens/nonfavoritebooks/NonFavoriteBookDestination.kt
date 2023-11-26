package com.thebrownfoxx.books.ui.screens.nonfavoritebooks

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.ui.screens.book.BookDestination
import com.thebrownfoxx.books.ui.screens.book.BookNavArgs
import com.thebrownfoxx.books.ui.screens.destinations.NonFavoriteEditBookDestination
import com.thebrownfoxx.books.ui.screens.navhost.BooksNavGraph

@BooksNavGraph
@Destination(navArgsDelegate = BookNavArgs::class)
@Composable
fun NonFavoriteBook(
    navigator: DestinationsNavigator,
    showSnackbarMessage: (String) -> Unit,
) {
    BookDestination(
        onNavigateUp = { navigator.navigateUp() },
        onEdit = { book -> navigator.navigate(NonFavoriteEditBookDestination(bookId = book.id)) },
        showSnackbarMessage = showSnackbarMessage,
    )
}