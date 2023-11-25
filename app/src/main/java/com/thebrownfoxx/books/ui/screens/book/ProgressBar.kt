package com.thebrownfoxx.books.ui.screens.book

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.theme.AppTheme
import com.thebrownfoxx.components.HorizontalSpacer

@Composable
fun ProgressBar(
    book: Book,
    newPagesRead: Int?,
    onNewPagesReadChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val progressColor = colorScheme.primaryContainer
    val progress by animateFloatAsState(book.readingProgress, label = "")

    Card(modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = progressColor,
                        size = size.copy(width = size.width * progress),
                    )
                }
                .padding(16.dp)
        ) {
            // TODO: Change outline on focus
            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.Transparent,
                    contentColor = colorScheme.onPrimaryContainer,
                ),
                shape = OutlinedTextFieldDefaults.shape,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 24.dp)
                        .animateContentSize(),
                ) {
                    if (newPagesRead == null) {
                        Text(
                            text = book.pagesRead.toString(),
                            color = colorScheme.onSurfaceVariant,
                        )
                    }
                    BasicTextField(
                        value = newPagesRead?.toString() ?: "",
                        onValueChange = onNewPagesReadChange,
                        modifier = Modifier.width(IntrinsicSize.Min),
                        textStyle = LocalTextStyle.current,
                    )
                }
            }
            HorizontalSpacer(width = 16.dp)
            Text(text = "/")
            HorizontalSpacer(width = 8.dp)
            Text(text = book.pages.toString())
        }
    }
}

@Preview
@Composable
fun ProgressBarPreview() {
    AppTheme {
        ProgressBar(
            book = Sample.Book,
            newPagesRead = 8,
            onNewPagesReadChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}