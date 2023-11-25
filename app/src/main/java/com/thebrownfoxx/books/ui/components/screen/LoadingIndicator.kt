package com.thebrownfoxx.books.ui.components.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.books.ui.theme.AppTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun LoadingIndicatorListPreview() {
    AppTheme {
        LoadingIndicator()
    }
}