package com.thebrownfoxx.books.ui.screens.navhost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Archive
import androidx.compose.material.icons.twotone.LibraryBooks
import androidx.compose.material.icons.twotone.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.thebrownfoxx.books.ui.screens.NavGraph
import com.thebrownfoxx.books.ui.screens.NavGraphs

enum class NavigationBarGraph(
    val graph: NavGraph,
    val icon: ImageVector,
    val label: String,
) {
    Books(
        graph = NavGraphs.books,
        icon = Icons.TwoTone.LibraryBooks,
        label = "Books",
    ),
    Favorites(
        graph = NavGraphs.favorites,
        icon = Icons.TwoTone.Star,
        label = "Favorites",
    ),
    Archive(
        graph = NavGraphs.archive,
        icon = Icons.TwoTone.Archive,
        label = "Archive",
    ),
}

