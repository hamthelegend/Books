package com.thebrownfoxx.books.ui.components

sealed class DialogState<T> {
    class Hidden<T> : DialogState<T>()
    data class Pending<T>(val value: T) : DialogState<T>()
    class Canceled<T> : DialogState<T>()
    class Confirmed<T> : DialogState<T>()
}

class DialogStateChangeListener<T>(
    val onInitiateAction: suspend (T) -> Boolean,
    val onCancelAction: () -> Unit,
    val onCommitAction: () -> Unit,
) {
    companion object {
        fun <T> empty() = DialogStateChangeListener<T>(
            onInitiateAction = { true },
            onCancelAction = {},
            onCommitAction = {},
        )
    }
}