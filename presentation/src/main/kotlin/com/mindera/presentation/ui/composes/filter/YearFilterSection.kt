package com.mindera.presentation.ui.composes.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindera.presentation.R

@Composable
internal fun YearFilterSection(
    years: List<Int>,
    selected: Set<Int>,
    onYearToggle: (Int) -> Unit
) {
    Text(stringResource(R.string.year), style = MaterialTheme.typography.labelLarge)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(years.sortedDescending()) { year ->
            FilterChip(
                modifier = Modifier.testTag("chip_$year"),
                selected = selected.contains(year),
                onClick = { onYearToggle(year) },
                label = { Text(year.toString()) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YearFilterSectionPreview() {
    MaterialTheme {
        YearFilterSection(listOf(2022, 2021, 2020), setOf(2022, 2021)) {}
    }
}

