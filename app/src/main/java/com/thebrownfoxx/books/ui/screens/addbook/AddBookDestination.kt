package com.thebrownfoxx.books.ui.screens.addbook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.application
import com.thebrownfoxx.books.ui.screens.navhost.BooksNavGraph

@BooksNavGraph
@Destination
@Composable
fun AddBook(navigator: DestinationsNavigator) {
    val viewModel = viewModel { AddBookViewModel(application.database) }

    with(viewModel) {
        val state by state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            navigateUp.collect {
                navigator.navigateUp()
            }
        }

        AddBookScreen(
            state = state,
            stateChangeListener = AddBookStateChangeListener(
                onTitleChange = ::updateTitle,
                onAuthorChange = ::updateAuthor,
                onDatePublishedPickerVisibleChange = ::updateDatePublishedPickerVisible,
                onDatePublishedChange = ::updateDatePublished,
                onPagesChange = ::updatePages,
                onAddBook = ::addBook,
            ),
            onNavigateUp = { navigator.navigateUp() },
        )
    }
}