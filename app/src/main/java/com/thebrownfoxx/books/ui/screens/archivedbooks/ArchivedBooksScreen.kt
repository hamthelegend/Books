package com.thebrownfoxx.books.ui.screens.archivedbooks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        title = "Books",
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        listState = books.getListState(
            emptyText = "No books added",
            searching = searchQuery != "",
        ),
        contentPadding = PaddingValues(vertical = 16.dp),
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
        )
    }
}