package com.thebrownfoxx.books.ui.components.domain

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Book
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.theme.AppTheme
import com.thebrownfoxx.components.HorizontalSpacer

@Composable
fun BookContent(
    book: Book,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.TwoTone.Book, contentDescription = null)
        HorizontalSpacer(width = 16.dp)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Row {
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = " · ${book.datePublished.year}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val progressColor = MaterialTheme.colorScheme.primaryContainer
    val progress by animateFloatAsState(book.readingProgress, label = "")

    val contentModifier = Modifier
        .drawBehind {
            drawRect(
                color = progressColor,
                size = size.copy(width = size.width * progress),
            )
        }
        .padding(16.dp)

    if (onClick != null) {
        Card(onClick = onClick, modifier = modifier) {
            BookContent(book = book, modifier = contentModifier)
        }
    } else {
        Card(modifier = modifier) {
            BookContent(book = book, modifier = contentModifier)
        }
    }
}

@Preview
@Composable
fun BookCardPreview() {
    AppTheme {
        BookCard(
            book = Sample.Book,
            modifier = Modifier.padding(16.dp),
        )
    }
}