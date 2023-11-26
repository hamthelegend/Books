package com.thebrownfoxx.books.ui.screens.favoritebooks

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.ui.screens.book.BookDestination
import com.thebrownfoxx.books.ui.screens.book.BookNavArgs
import com.thebrownfoxx.books.ui.screens.destinations.FavoriteEditBookDestination
import com.thebrownfoxx.books.ui.screens.navhost.FavoritesNavGraph

@FavoritesNavGraph
@Destination(navArgsDelegate = BookNavArgs::class)
@Composable
fun FavoriteBook(
    navigator: DestinationsNavigator,
    showSnackbarMessage: (String) -> Unit,
) {
    BookDestination(
        onNavigateUp = { navigator.navigateUp() },
        onEdit = { book -> navigator.navigate(FavoriteEditBookDestination(bookId = book.id)) },
        showSnackbarMessage = showSnackbarMessage,
    )
}