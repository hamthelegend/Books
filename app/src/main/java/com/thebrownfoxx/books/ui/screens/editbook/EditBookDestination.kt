package com.thebrownfoxx.books.ui.screens.editbook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.application

@Composable
fun EditBook(navigator: DestinationsNavigator) {
    val viewModel = viewModel {
        EditBookViewModel(
            database = application.database,
            savedStateHandle = createSavedStateHandle(),
        )
    }

    with(viewModel) {
        val state by state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            navigateUp.collect { navigator.navigateUp() }
        }

        EditBookScreen(
            state = state,
            stateChangeListener = EditBookStateChangeListener(
                onTitleChange = ::updateTitle,
                onAuthorChange = ::updateAuthor,
                onDatePublishedPickerVisibleChange = ::updateDatePublishedPickerVisible,
                onDatePublishedChange = ::updateDatePublished,
                onPagesChange = ::updatePages,
                onSaveChanges = ::saveChanges,
            ),
            onNavigateUp = { navigator.navigateUp() },
        )
    }
}