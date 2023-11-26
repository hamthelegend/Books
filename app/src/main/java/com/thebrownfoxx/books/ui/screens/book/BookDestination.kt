package com.thebrownfoxx.books.ui.screens.book

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thebrownfoxx.books.application
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import kotlinx.coroutines.launch


@Composable
fun BookDestination(
    onNavigateUp: () -> Unit,
    onEdit: (Book) -> Unit,
    showSnackbarMessage: (String) -> Unit,
) {
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
        val deleteDialogVisible by deleteDialogVisible.collectAsStateWithLifecycle()

        LaunchedEffect(navigateUp, bookEvent) {
            launch {
                navigateUp.collect { onNavigateUp() }
            }
            launch {
                bookEvent.collect { event ->
                    val bookTitle = book?.title ?: "book"
                    showSnackbarMessage(
                        when (event) {
                            BookEvent.UpdatedPagesRead -> "$bookTitle's read progress has been updated"
                            BookEvent.Favorited -> "$bookTitle has been favorited"
                            BookEvent.Unfavorited -> "$bookTitle has been unfavorited"
                            BookEvent.Archived -> "$bookTitle has been archived"
                            BookEvent.Unarchived -> "$bookTitle has been unarchived"
                            BookEvent.Deleted -> "$bookTitle has been deleted"
                        }
                    )
                }
            }
        }

        BookScreen(
            book = book ?: Sample.Book,
            newPagesRead = newPagesRead,
            onNewPagesReadChange = ::updateNewPagesRead,
            savePagesReadButtonVisible = savePagesReadButtonVisible,
            onSavePagesRead = ::savePagesRead,
            onEdit = { book?.let(onEdit) },
            onFavorite = ::favorite,
            onUnfavorite = ::unfavorite,
            onArchive = ::archive,
            onUnarchive = ::unarchive,
            deleteDialogVisible = deleteDialogVisible,
            onDeleteDialogVisibleChange = ::updateDeleteDialogVisible,
            onDelete = ::delete,
            onNavigateUp = onNavigateUp,
        )
    }
}