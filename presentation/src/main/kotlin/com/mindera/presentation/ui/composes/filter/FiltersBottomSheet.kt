package com.mindera.presentation.ui.composes.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindera.designsystem.VSpacer
import com.mindera.presentation.R
import com.mindera.presentation.models.Filter

@Composable
fun LaunchFilterBottomSheet(
    allYears: List<Int>,
    selectedYears: Set<Int>,
    isSuccessOnly: Boolean,
    isAscending: Boolean,
    onApply: (Filter) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedYearsState by remember { mutableStateOf(selectedYears) }
    var isSuccessState by remember { mutableStateOf(isSuccessOnly) }
    var isAscState by remember { mutableStateOf(isAscending) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.filters), style = MaterialTheme.typography.titleMedium)

        VSpacer(12.dp)
        YearFilterSection(allYears, selectedYearsState) { year ->
            val isSelected = selectedYearsState.contains(year)
            selectedYearsState = selectedYearsState.toMutableSet().apply {
                if (isSelected) remove(year) else add(year)
            }.toSet()
        }
        VSpacer(16.dp)
        SuccessFilterSwitch(isSuccessState) {
            isSuccessState = it
        }
        VSpacer(16.dp)
        SortOrderToggle(isAscState) {
            isAscState = it
        }
        FilterBottomActions(
            onApply = {
                onApply(
                    Filter(
                        years = selectedYearsState,
                        launchSuccess = isSuccessState,
                        isAscending = isAscState
                    )
                )
            },
            onCancel = onDismiss
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchFilterBottomSheetPreview() {
    MaterialTheme {
        LaunchFilterBottomSheet(
            allYears = listOf(2022, 2023, 2024),
            selectedYears = setOf(2022, 2023),
            isSuccessOnly = true,
            isAscending = true,
            onApply = { },
            onDismiss = {})
    }

}