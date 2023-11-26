package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.application
import com.thebrownfoxx.books.ui.components.DialogStateChangeListener
import com.thebrownfoxx.books.ui.screens.destinations.ArchivedBookDestination
import com.thebrownfoxx.books.ui.screens.navhost.ArchiveNavGraph

@ArchiveNavGraph(start = true)
@Destination
@Composable
fun ArchivedBooks(
    navigator: DestinationsNavigator,
    showSnackbarMessage: (String) -> Unit,
) {
    val viewModel = viewModel {
        ArchivedBooksViewModel(application.database)
    }
    
    with(viewModel) {
        val books by books.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val archiveDialogState by deleteDialogState.collectAsStateWithLifecycle()
        val unarchiveAllDialogVisible by unarchiveAllDialogVisible.collectAsStateWithLifecycle()
        val deleteAllDialogVisible by deleteAllDialogVisible.collectAsStateWithLifecycle()

        LaunchedEffect(archivedBooksEvent) {
            archivedBooksEvent.collect { event ->
                showSnackbarMessage(
                    when (event) {
                        is ArchivedBooksEvent.Delete -> "${event.book.title} has been deleted"
                        ArchivedBooksEvent.UnarchiveAll -> "All archived books have been unarchived"
                        ArchivedBooksEvent.DeleteAll -> "All archived books have been deleted"
                    }
                )
            }
        }
        
        ArchivedBooksScreen(
            books = books,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            onOpenBook = { navigator.navigate(ArchivedBookDestination(bookId = it.id)) },
            archiveDialogState = archiveDialogState,
            archiveDialogStateChangeListener = DialogStateChangeListener(
                onInitiateAction = ::initiateDelete,
                onCancelAction = ::cancelDelete,
                onCommitAction = ::delete,
            ),
            unarchiveAllDialogVisible = unarchiveAllDialogVisible,
            onUnarchiveAllDialogVisibleChange = ::updateUnarchiveAllDialogVisible,
            onUnarchiveAll = ::unarchiveAll,
            deleteAllDialogVisible = deleteAllDialogVisible,
            onDeleteAllDialogVisibleChange = ::updateDeleteAllDialogVisible,
            onDeleteAll = ::deleteAll,
        )
    }
}