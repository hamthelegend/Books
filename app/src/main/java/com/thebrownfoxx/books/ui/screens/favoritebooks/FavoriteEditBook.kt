package com.thebrownfoxx.books.ui.screens.favoritebooks

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.books.ui.screens.editbook.EditBook
import com.thebrownfoxx.books.ui.screens.editbook.EditBookNavArgs
import com.thebrownfoxx.books.ui.screens.navhost.FavoritesNavGraph

@FavoritesNavGraph
@Destination(navArgsDelegate = EditBookNavArgs::class)
@Composable
fun FavoriteEditBook(navigator: DestinationsNavigator) {
    EditBook(navigator = navigator)
}