package com.thebrownfoxx.books.ui.screens.navhost

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.thebrownfoxx.components.extension.Zero
import com.thebrownfoxx.books.ui.components.getDefaultTransitions

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun NavHost(modifier: Modifier = Modifier) {
    val engine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = getDefaultTransitions(LocalDensity.current),
    )
    var navigationBarGraph by rememberSaveable { mutableStateOf(NavigationBarGraph.Books) }

    Scaffold(
        modifier = modifier.imePadding(),
        contentWindowInsets = WindowInsets.Zero,
        bottomBar = {
            NavigationBar(
                currentNavGraph = navigationBarGraph,
                onNavGraphChange = { navigationBarGraph = it },
            )
        },
    ) { contentPadding ->
        AnimatedContent(targetState = navigationBarGraph, label = "") { graph ->
            DestinationsNavHost(
                engine = engine,
                navGraph = graph.graph,
                modifier = Modifier.padding(contentPadding),
            )
        }
    }
}