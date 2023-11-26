package com.thebrownfoxx.books.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hamthelegend.enchantmentorder.extensions.toLocalDate
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    initialDate: LocalDate?,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    LaunchedEffect(initialDate) {
        if (initialDate != null) {
            state.setSelection(
                initialDate
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )
        }
    }

    if (visible) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = onDismissRequest,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Confirm",
                    onClick = {
                        val date = Instant.ofEpochMilli(state.selectedDateMillis!!).toLocalDate()
                        onConfirm(date)
                    },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                )
            },
        ) {
            DatePicker(state = state)
        }
    }
}