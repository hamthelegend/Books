package com.thebrownfoxx.books.ui.components.domain

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.thebrownfoxx.books.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableBookCard(
    book: Book,
    onClick: () -> Unit,
    onDismiss: suspend () -> Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val dismissState = rememberDismissState()

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue != DismissValue.Default) {
            val removed = onDismiss()
            if (!removed) dismissState.reset()
        }
    }

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {},
        dismissContent = {
            BookCard(
                book = book,
                onClick = onClick,
                modifier = Modifier.padding(contentPadding),
            )
        },
    )
}