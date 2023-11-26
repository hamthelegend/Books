package com.thebrownfoxx.books.ui.screens.addbook

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.ui.components.DatePickerDialog
import com.thebrownfoxx.books.ui.components.TextField
import com.thebrownfoxx.books.ui.components.modifier.bringIntoViewOnFocus
import com.thebrownfoxx.books.ui.components.modifier.indicationlessClickable
import com.thebrownfoxx.books.ui.extensions.formatted
import com.thebrownfoxx.books.ui.theme.AppTheme
import com.thebrownfoxx.components.ExpandedTopAppBar
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.extension.Zero
import com.thebrownfoxx.components.extension.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    state: AddBookState,
    stateChangeListener: AddBookStateChangeListener,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = "Add book"
    val focusRequesters = remember { Array(5) { FocusRequester() } }
    val focusManger = LocalFocusManager.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    fun Modifier.fieldModifier(focusIndex: Int) = bringIntoViewOnFocus {
        val topAppBarState = scrollBehavior.state
        AnimationState(initialValue = topAppBarState.heightOffset).animateTo(
            targetValue = topAppBarState.heightOffsetLimit,
            animationSpec = scrollBehavior.snapAnimationSpec!!,
        ) { topAppBarState.heightOffset = value }
    }
        .focusRequester(focusRequesters[focusIndex])
        .focusProperties {
            next = focusRequesters.getOrElse(focusIndex + 1) { focusRequesters.first() }
        }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .indicationlessClickable { focusManger.clearFocus() },
        topBar = {
            ExpandedTopAppBar(
                collapsedContent = { Text(text = title) },
                navigationIcon = {
                    IconButton(
                        imageVector = Icons.TwoTone.ArrowBack,
                        contentDescription = null,
                        onClick = onNavigateUp,
                    )
                },
                scrollBehavior = scrollBehavior,
            ) {
                Text(text = title)
            }
        },
        contentWindowInsets = WindowInsets.Zero,
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding + PaddingValues(16.dp)),
        ) {
            TextField(
                label = "Title",
                value = state.title,
                onValueChange = stateChangeListener.onTitleChange,
                required = true,
                error = if (state.showTitleRequired) "Required" else null,
                modifier = Modifier.fieldModifier(focusIndex = 0),
            )
            TextField(
                label = "Author",
                value = state.author,
                onValueChange = stateChangeListener.onAuthorChange,
                required = true,
                error = if (state.showAuthorRequired) "Required" else null,
                modifier = Modifier.fieldModifier(focusIndex = 1),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                TextField(
                    label = "Date published",
                    value = state.datePublished?.formatted() ?: "",
                    onValueChange = stateChangeListener.onAuthorChange,
                    readOnly = true,
                    required = true,
                    onClick = { stateChangeListener.onDatePublishedPickerVisibleChange(true) },
                    error = if (state.showDatePublishedRequired) "Required" else null,
                    modifier = Modifier
                        .weight(2f)
                        .fieldModifier(focusIndex = 2)
                        .onFocusChanged {
                            if (it.isFocused) {
                                stateChangeListener.onDatePublishedPickerVisibleChange(true)
                            }
                        },
                )
                TextField(
                    label = "Pages",
                    value = state.pages?.toString() ?: "",
                    onValueChange = stateChangeListener.onPagesChange,
                    required = true,
                    error = if (state.showPagesRequired) "Required" else null,
                    numeric = true,
                    modifier = Modifier
                        .weight(1f)
                        .fieldModifier(focusIndex = 3),
                )
            }
            FilledButton(
                text = "Save",
                onClick = stateChangeListener.onAddBook,
                modifier = Modifier
                    .fillMaxWidth()
                    .fieldModifier(focusIndex = 4),
            )
        }
    }

    DatePickerDialog(
        initialDate = state.datePublished,
        visible = state.datePublishedPickerVisible,
        onDismissRequest = { stateChangeListener.onDatePublishedPickerVisibleChange(false) },
        onConfirm = { stateChangeListener.onDatePublishedChange(it) },
    )
}

@Preview
@Composable
fun AddBookScreenPreview() {
    AppTheme {
        AddBookScreen(
            state = AddBookState(),
            stateChangeListener = AddBookStateChangeListener.empty(),
            onNavigateUp = {},
        )
    }
}