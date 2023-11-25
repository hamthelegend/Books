package com.thebrownfoxx.books.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.books.ui.components.modifier.clicks

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: String? = null,
    error: String? = null,
    isError: Boolean = error != null,
    enabled: Boolean = true,
    required: Boolean = false,
    numeric: Boolean = false,
    readOnly: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val clicks = interactionSource.clicks
    LaunchedEffect(Unit) {
        clicks.collect { onClick() }
    }

    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = label?.let {
                {
                    Row {
                        Text(text = it)
                        if (required) Text(text = "*", color = colorScheme.error)
                    }
                }
            },
            enabled = enabled,
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default
                .copy(imeAction = ImeAction.Next)
                .run {
                if (numeric) copy(keyboardType = KeyboardType.Number)
                else this
            },
            readOnly = readOnly,
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
        )
        AnimatedVisibility(visible = error != null) {
            Text(
                text = error ?: "",
                color = colorScheme.error,
                style = typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }
    }
}