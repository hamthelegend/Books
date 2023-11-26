package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.ui.screens.editbook.EditBook
import com.thebrownfoxx.books.ui.screens.editbook.EditBookNavArgs
import com.thebrownfoxx.books.ui.screens.navhost.ArchiveNavGraph

@ArchiveNavGraph
@Destination(navArgsDelegate = EditBookNavArgs::class)
@Composable
fun ArchivedEditBook(
    navigator: DestinationsNavigator,
    showSnackbarMessage: (String) -> Unit,
) {
    EditBook(
        navigator = navigator,
        showSnackbarMessage = showSnackbarMessage,
    )
}