package com.mindera.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseSnackBar(
    modifier: Modifier = Modifier,
    snackBarState: SnackbarHostState,
    actionText: String? = null,
    onActionClick: (() -> Unit?)? = null,
    ) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackBarState
    ) { snackBarData ->
        Snackbar(
            action = {
                Text(
                    text = actionText ?: "dismiss",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .clickable {
                            onActionClick?.invoke()
                            snackBarData.dismiss()
                        }
                        .padding(8.dp)
                )
            }
        ) {
            Text(text = snackBarData.visuals.message, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
