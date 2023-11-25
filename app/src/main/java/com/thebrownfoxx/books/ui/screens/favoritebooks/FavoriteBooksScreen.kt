package com.thebrownfoxx.books.ui.screens.favoritebooks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.components.domain.BookCard
import com.thebrownfoxx.books.ui.components.screen.SearchableLazyColumnScreen
import com.thebrownfoxx.books.ui.components.screen.getListState
import com.thebrownfoxx.books.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteBooksScreen(
    books: List<Book>?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onOpenBook: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        title = "Books",
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        listState = books.getListState(
            emptyText = "No favorite books",
            searching = searchQuery != "",
        ),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = books ?: emptyList(),
            key = { it.id }
        ) { book ->
            BookCard(
                book = book,
                onClick = { onOpenBook(book) },
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }
}

@Preview
@Composable
fun FavoriteBooksScreenPreview() {
    AppTheme {
        FavoriteBooksScreen(
            books = Sample.Books,
            searchQuery = "",
            onSearchQueryChange = {},
            onOpenBook = {},
        )
    }
}