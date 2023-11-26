package com.thebrownfoxx.books.ui.screens.book

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.components.modifier.indicationlessClickable
import com.thebrownfoxx.books.ui.theme.AppTheme
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.extension.rememberMutableStateOf

@Composable
fun ProgressBar(
    book: Book,
    newPagesRead: Int?,
    onNewPagesReadChange: (String) -> Unit,
    editable: Boolean,
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
            ReadPagesTextField(
                newPagesRead = newPagesRead,
                book = book,
                onNewPagesReadChange = onNewPagesReadChange,
                enabled = editable,
            )
            HorizontalSpacer(width = 16.dp)
            Text(text = "out of ${book.pages}")
        }
    }
}

@Composable
private fun ReadPagesTextField(
    newPagesRead: Int?,
    book: Book,
    onNewPagesReadChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    var textFieldInFocus by rememberMutableStateOf(false)
    val textFieldOutlineColor by animateColorAsState(
        targetValue = when {
            textFieldInFocus -> colorScheme.primary
            enabled -> colorScheme.outline
            else -> colorScheme.outline.copy(alpha = 0.2f)
        },
        label = "",
    )
    val textFieldOutlineWidth by animateDpAsState(
        targetValue = if (textFieldInFocus) 2.dp else 1.dp,
        label = "",
    )
    val placeholderAlpha by animateFloatAsState(
        targetValue = if (textFieldInFocus) 0.6f else 1f,
        label = "",
    )

    OutlinedCard(
        border = BorderStroke(width = textFieldOutlineWidth, color = textFieldOutlineColor),
        shape = OutlinedTextFieldDefaults.shape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = colorScheme.onPrimaryContainer,
        ),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .indicationlessClickable { focusRequester.requestFocus() }
                .padding(vertical = 16.dp, horizontal = 24.dp)
                .animateContentSize()
                .onFocusChanged { textFieldInFocus = it.isFocused },
        ) {
            if (newPagesRead == null) {
                Text(
                    text = book.pagesRead.toString(),
                    color = colorScheme.onSurfaceVariant.copy(alpha = placeholderAlpha),
                )
            }
            BasicTextField(
                value = newPagesRead?.toString() ?: "",
                onValueChange = onNewPagesReadChange,
                textStyle = LocalTextStyle
                    .current
                    .copy(color = colorScheme.onSecondaryContainer),
                keyboardOptions = KeyboardOptions.Default
                    .copy(keyboardType = KeyboardType.Number),
                cursorBrush = SolidColor(colorScheme.primary),
                enabled = enabled,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .width(IntrinsicSize.Min),
            )
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
            editable = true,
            modifier = Modifier.padding(16.dp),
        )
    }
}