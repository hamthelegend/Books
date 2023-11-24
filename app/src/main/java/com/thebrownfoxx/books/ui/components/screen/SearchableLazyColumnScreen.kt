package com.thebrownfoxx.books.ui.components.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.extension.Zero
import com.thebrownfoxx.components.extension.plus

fun <T> List<T>?.getListState(emptyText: String, searching: Boolean) = when {
    this == null -> ListState.Loading
    isEmpty() && searching -> ListState.NoSearchResults
    isEmpty() -> ListState.Empty(text = emptyText)
    else -> ListState.Loaded
}

sealed class ListState {
    data object Loading : ListState()
    data class Empty(val text: String) : ListState()
    data object NoSearchResults : ListState()
    data object Loaded : ListState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableLazyColumnScreen(
    topBarExpandedContent: @Composable BoxScope.() -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    listState: ListState = ListState.Loaded,
    onNavigateUp: (() -> Unit)? = null,
    background: @Composable BoxScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: LazyListScope.() -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar,
        contentWindowInsets = WindowInsets.Zero,
        topBar = {
            SearchTopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onNavigateUp = onNavigateUp,
                background = background,
                scrollBehavior = scrollBehavior,
                content = topBarExpandedContent,
            )
        },
    ) { scaffoldContentPadding ->
        when (listState) {
            ListState.Loading -> LoadingIndicator(
                modifier = Modifier.padding(scaffoldContentPadding),
            )

            is ListState.Empty -> EmptyList(
                text = listState.text,
                modifier = Modifier.padding(scaffoldContentPadding),
            )

            ListState.NoSearchResults -> NoSearchResults(
                modifier = Modifier.padding(scaffoldContentPadding),
            )

            ListState.Loaded -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = content,
                contentPadding = scaffoldContentPadding + contentPadding,
                state = lazyListState,
                verticalArrangement = verticalArrangement,
            )
        }
    }

    LaunchedEffect(searchQuery) {
        lazyListState.animateScrollToItem(0)
    }
}

@Composable
fun SearchableLazyColumnScreen(
    title: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    listState: ListState = ListState.Loaded,
    onNavigateUp: (() -> Unit)? = null,
    background: @Composable BoxScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: LazyListScope.() -> Unit,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        topBarExpandedContent = { Text(text = title) },
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        listState = listState,
        onNavigateUp = onNavigateUp,
        background = background,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar,
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding,
        content = content,
    )
}