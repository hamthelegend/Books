package com.thebrownfoxx.books.ui.screens.nonfavoritebooks

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.ui.screens.editbook.EditBook
import com.thebrownfoxx.books.ui.screens.editbook.EditBookNavArgs
import com.thebrownfoxx.books.ui.screens.navhost.BooksNavGraph

@BooksNavGraph
@Destination(navArgsDelegate = EditBookNavArgs::class)
@Composable
fun NonFavoriteEditBook(
    navigator: DestinationsNavigator,
    showSnackbarMessage: (String) -> Unit,
) {
    EditBook(
        navigator = navigator,
        showSnackbarMessage = showSnackbarMessage,
    )
}