package com.thebrownfoxx.books.ui.screens.nonfavoritebooks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.components.domain.SwipeableBookCard
import com.thebrownfoxx.books.ui.components.screen.SearchableLazyColumnScreen
import com.thebrownfoxx.books.ui.components.screen.getListState
import com.thebrownfoxx.books.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NonFavoriteBooksScreen(
    books: List<Book>?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddBook: () -> Unit,
    onOpenBook: (Book) -> Unit,
    onArchive: (Book) -> Unit,
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
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBook) {
                Icon(imageVector = Icons.TwoTone.Add, contentDescription = null)
            }
        }
    ) {
        items(
            items = books ?: emptyList(),
            key = { it.id }
        ) { book ->
            SwipeableBookCard(
                book = book,
                onClick = { onOpenBook(book) },
                onDismiss = { onArchive(book); true },
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }
}

@Preview
@Composable
fun NonFavoriteBooksScreenPreview() {
    AppTheme {
        NonFavoriteBooksScreen(
            books = Sample.Books,
            searchQuery = "",
            onSearchQueryChange = {},
            onAddBook = {},
            onOpenBook = {},
            onArchive = {},
        )
    }
}