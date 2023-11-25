package com.thebrownfoxx.books.ui.screens.book

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.application
import com.thebrownfoxx.books.model.Sample


@Composable
fun BookDestination(navigator: DestinationsNavigator) {
    val viewModel = viewModel {
        BookViewModel(
            database = application.database,
            savedStateHandle = createSavedStateHandle(),
        )
    }

    with(viewModel) {
        val book by book.collectAsStateWithLifecycle()
        val newPagesRead by newPagesRead.collectAsStateWithLifecycle()
        val savePagesReadButtonVisible by savePagesReadButtonVisible.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            navigateUp.collect { navigator.navigateUp() }
        }

        BookScreen(
            book = book ?: Sample.Book,
            newPagesRead = newPagesRead,
            onNewPagesReadChange = ::updateNewPagesRead,
            savePagesReadButtonVisible = savePagesReadButtonVisible,
            onSavePagesRead = ::savePagesRead,
            onFavorite = ::favorite,
            onUnfavorite = ::unfavorite,
            onArchive = ::archive,
            onUnarchive = ::unarchive,
            onDelete = ::delete,
            onNavigateUp = { navigator.navigateUp() },
        )
    }
}