package com.thebrownfoxx.books.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.ui.theme.AppTheme

@Composable
fun LabeledText(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
        )
        Text(text = text)
    }
}

@Preview
@Composable
fun LabeledTextPreview() {
    AppTheme {
        LabeledText(
            label = "Title",
            text = "Come Hang Out",
            modifier = Modifier.padding(16.dp),
        )
    }
}