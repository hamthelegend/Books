package com.thebrownfoxx.books.ui.screens.book

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Archive
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.model.Book
import com.thebrownfoxx.books.model.Sample
import com.thebrownfoxx.books.ui.components.LabeledText
import com.thebrownfoxx.books.ui.components.modifier.bringIntoViewOnFocus
import com.thebrownfoxx.books.ui.extensions.formatted
import com.thebrownfoxx.books.ui.theme.AppTheme
import com.thebrownfoxx.components.ExpandedTopAppBar
import com.thebrownfoxx.components.FilledIconButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    book: Book,
    newPagesRead: Int?,
    onNewPagesReadChange: (String) -> Unit,
    savePagesReadButtonVisible: Boolean,
    onSavePagesRead: () -> Unit,
    onArchive: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpandedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(
                        imageVector = Icons.TwoTone.ArrowBack,
                        contentDescription = null,
                        onClick = onNavigateUp,
                    )
                },
                collapsedContent = {
                    Column {
                        Text(
                            text = book.title,
                            style = typography.titleMedium,
                        )
                        Text(
                            text = book.author,
                            style = typography.labelSmall,
                        )
                    }
                },
                actions = {
                    IconButton(
                        imageVector = Icons.TwoTone.Archive,
                        contentDescription = null,
                        onClick = onArchive,
                    )
                },
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = book.title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = typography.headlineSmall,
                    )
                    Text(
                        text = book.author,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = typography.titleSmall,
                    )
                }
            }
        }
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding + PaddingValues(16.dp)),
        ) {
            LabeledText(label = "Published", text = book.datePublished.formatted())
            LabeledText(label = "Added to realm", text = book.dateAdded.formatted())
            if (book.dateModified != null) {
                LabeledText(label = "Last modified", text = book.dateModified.formatted())
            }
            Column {
                Text(
                    text = "Pages read".uppercase(),
                    style = typography.labelSmall,
                )
                VerticalSpacer(height = 8.dp)
                // TODO: Scroll on focus
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProgressBar(
                        book = book,
                        newPagesRead = newPagesRead,
                        onNewPagesReadChange = onNewPagesReadChange,
                        modifier = Modifier
                            .weight(1f)
                            .bringIntoViewOnFocus {
                                val topAppBarState = scrollBehavior.state
                                AnimationState(initialValue = topAppBarState.heightOffset).animateTo(
                                    targetValue = topAppBarState.heightOffsetLimit,
                                    animationSpec = scrollBehavior.snapAnimationSpec!!,
                                ) { topAppBarState.heightOffset = value }
                            },
                    )
                    AnimatedVisibility(visible = savePagesReadButtonVisible) {
                        FilledIconButton(
                            imageVector = Icons.TwoTone.Check,
                            contentDescription = null,
                            onClick = onSavePagesRead,
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BookScreenPreview() {
    AppTheme {
        BookScreen(
            book = Sample.Book,
            newPagesRead = 8,
            savePagesReadButtonVisible = true,
            onNewPagesReadChange = {},
            onSavePagesRead = {},
            onArchive = {},
            onNavigateUp = {},
        )
    }
}