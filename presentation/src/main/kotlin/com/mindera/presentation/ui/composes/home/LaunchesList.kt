package com.mindera.presentation.ui.composes.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mindera.presentation.models.LaunchUi

@Composable
internal fun LaunchesList(launchesList: List<LaunchUi>, onClick: (String, String, String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items = launchesList) { launch ->
            LaunchItem(launch, onClick)
        }

    }

}