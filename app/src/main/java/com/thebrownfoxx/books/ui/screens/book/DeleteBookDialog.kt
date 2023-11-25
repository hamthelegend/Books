package com.thebrownfoxx.books.ui.screens.book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.ui.components.domain.BookCard
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer

@Composable
fun DeleteBookDialog(
    book: Book,
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (visible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            icon = {
                Icon(imageVector = Icons.TwoTone.DeleteForever, contentDescription = null)
            },
            title = {
                Text(
                    text = "Delete book",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            text = {
                Column {
                    Text(text = "Are you sure you want to delete this book? It will be gone from the realm forever.")
                    VerticalSpacer(height = 16.dp)
                    BookCard(book = book)
                }
            },
            dismissButton = {
                TextButton(
                    text = "No",
                    onClick = onDismiss,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Yes",
                    onClick = onConfirm,
                )
            },
        )
    }
}