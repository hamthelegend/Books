package com.thebrownfoxx.books.ui.screens.navhost

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.thebrownfoxx.books.ui.components.getDefaultTransitions
import com.thebrownfoxx.books.ui.screens.addbook.AddBook
import com.thebrownfoxx.books.ui.screens.archivedbooks.ArchivedBook
import com.thebrownfoxx.books.ui.screens.archivedbooks.ArchivedBooks
import com.thebrownfoxx.books.ui.screens.archivedbooks.ArchivedEditBook
import com.thebrownfoxx.books.ui.screens.destinations.AddBookDestination
import com.thebrownfoxx.books.ui.screens.destinations.ArchivedBookDestination
import com.thebrownfoxx.books.ui.screens.destinations.ArchivedBooksDestination
import com.thebrownfoxx.books.ui.screens.destinations.ArchivedEditBookDestination
import com.thebrownfoxx.books.ui.screens.destinations.FavoriteBookDestination
import com.thebrownfoxx.books.ui.screens.destinations.FavoriteEditBookDestination
import com.thebrownfoxx.books.ui.screens.destinations.NonFavoriteBookDestination
import com.thebrownfoxx.books.ui.screens.destinations.NonFavoriteBooksDestination
import com.thebrownfoxx.books.ui.screens.destinations.NonFavoriteEditBookDestination
import com.thebrownfoxx.books.ui.screens.favoritebooks.FavoriteBook
import com.thebrownfoxx.books.ui.screens.favoritebooks.FavoriteEditBook
import com.thebrownfoxx.books.ui.screens.nonfavoritebooks.NonFavoriteBook
import com.thebrownfoxx.books.ui.screens.nonfavoritebooks.NonFavoriteBooks
import com.thebrownfoxx.books.ui.screens.nonfavoritebooks.NonFavoriteEditBook
import com.thebrownfoxx.components.extension.Zero
import com.thebrownfoxx.components.extension.rememberMutableStateOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun NavHost(modifier: Modifier = Modifier) {
    val engine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = getDefaultTransitions(LocalDensity.current),
    )
    var navigationBarGraph by rememberSaveable { mutableStateOf(NavigationBarGraph.Books) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var snackbarJob by rememberMutableStateOf<Job?>(null)

    fun showSnackbarMessage(message: String) {
        snackbarJob?.cancel()
        snackbarJob = scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        contentWindowInsets = WindowInsets.Zero,
        bottomBar = {
            NavigationBar(
                currentNavGraph = navigationBarGraph,
                onNavGraphChange = { navigationBarGraph = it },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                val dismissState = rememberDismissState(
                    confirmValueChange = { dismissValue ->
                        if (dismissValue != DismissValue.Default) {
                            snackbarJob?.cancel()
                        }
                        dismissValue != DismissValue.Default
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {},
                    dismissContent = {
                        Snackbar(snackbarData = snackbarData)
                    },
                )
            }
        },
    ) { contentPadding ->
        AnimatedContent(targetState = navigationBarGraph, label = "") { graph ->
            DestinationsNavHost(
                engine = engine,
                navGraph = graph.graph,
                modifier = Modifier.padding(contentPadding),
            ) {
                composable(NonFavoriteBooksDestination) {
                    NonFavoriteBooks(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(ArchivedBooksDestination) {
                    ArchivedBooks(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(AddBookDestination) {
                    AddBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(NonFavoriteBookDestination) {
                    NonFavoriteBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(FavoriteBookDestination) {
                    FavoriteBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(ArchivedBookDestination) {
                    ArchivedBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(NonFavoriteEditBookDestination) {
                    NonFavoriteEditBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(FavoriteEditBookDestination) {
                    FavoriteEditBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
                composable(ArchivedEditBookDestination) {
                    ArchivedEditBook(
                        navigator = destinationsNavigator,
                        showSnackbarMessage = ::showSnackbarMessage,
                    )
                }
            }
        }
    }
}