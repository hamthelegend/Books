package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DeleteSweep
import androidx.compose.material.icons.twotone.SettingsBackupRestore
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.components.DialogState
import com.thebrownfoxx.books.ui.components.DialogStateChangeListener
import com.thebrownfoxx.books.ui.components.domain.SwipeableBookCard
import com.thebrownfoxx.books.ui.components.screen.SearchableLazyColumnScreen
import com.thebrownfoxx.books.ui.components.screen.getListState
import com.thebrownfoxx.books.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArchivedBooksScreen(
    books: List<Book>?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onOpenBook: (Book) -> Unit,
    archiveDialogState: DialogState<Book>,
    archiveDialogStateChangeListener: DialogStateChangeListener<Book>,
    unarchiveAllDialogVisible: Boolean,
    onUnarchiveAllDialogVisibleChange: (Boolean) -> Unit,
    onUnarchiveAll: () -> Unit,
    deleteAllDialogVisible: Boolean,
    onDeleteAllDialogVisibleChange: (Boolean) -> Unit,
    onDeleteAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        title = "Archive",
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        listState = books.getListState(
            emptyText = "No archived books",
            searching = searchQuery != "",
        ),
        contentPadding = PaddingValues(vertical = 16.dp),
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SmallFloatingActionButton(
                    onClick = { onDeleteAllDialogVisibleChange(true) },
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.DeleteSweep,
                        contentDescription = null,
                    )
                }
                FloatingActionButton(onClick = { onUnarchiveAllDialogVisibleChange(true) }) {
                    Icon(
                        imageVector = Icons.TwoTone.SettingsBackupRestore,
                        contentDescription = null,
                    )
                }
            }
        },
    ) {
        items(
            items = books ?: emptyList(),
            key = { it.id }
        ) { book ->
            SwipeableBookCard(
                book = book,
                onClick = { onOpenBook(book) },
                onDismiss = { archiveDialogStateChangeListener.onInitiateAction(book) },
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }

    DeleteBookDialog(
        state = archiveDialogState,
        stateChangeListener = archiveDialogStateChangeListener,
    )

    UnarchiveAllDialog(
        visible = unarchiveAllDialogVisible,
        onDismiss = { onUnarchiveAllDialogVisibleChange(false) },
        onConfirm = onUnarchiveAll,
    )

    DeleteAllDialog(
        visible = deleteAllDialogVisible,
        onDismiss = { onDeleteAllDialogVisibleChange(false) },
        onConfirm = onDeleteAll,
    )
}

@Preview
@Composable
fun ArchivedBooksScreenPreview() {
    AppTheme {
        ArchivedBooksScreen(
            books = Sample.Books,
            searchQuery = "",
            onSearchQueryChange = {},
            onOpenBook = {},
            archiveDialogState = DialogState.Hidden(),
            archiveDialogStateChangeListener = DialogStateChangeListener.empty(),
            unarchiveAllDialogVisible = false,
            onUnarchiveAllDialogVisibleChange = {},
            onUnarchiveAll = {},
            deleteAllDialogVisible = false,
            onDeleteAllDialogVisibleChange = {},
            onDeleteAll = {},
        )
    }
}