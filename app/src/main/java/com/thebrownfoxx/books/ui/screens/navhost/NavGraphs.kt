package com.thebrownfoxx.books.ui.screens.navhost

import com.ramcosta.composedestinations.annotation.NavGraph

@NavGraph
annotation class BooksNavGraph(val start: Boolean = false)

@NavGraph
annotation class FavoritesNavGraph(val start: Boolean = false)

@NavGraph
annotation class ArchiveNavGraph(val start: Boolean = false)