package com.mindera.presentation.ui.composes.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mindera.presentation.R

@Composable
internal fun SortOrderToggle(
    isAscending: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.sort_order), modifier = Modifier.weight(1f))
        FilterChip(
            selected = isAscending,
            onClick = { onToggle(!isAscending) },
            label = { Text(if (isAscending) stringResource(R.string.asc) else stringResource(R.string.desc)) }
        )
    }
}


@Preview
@Composable
private fun SortOrderTogglePreview() {
    SortOrderToggle(isAscending = true, onToggle = {})
}
