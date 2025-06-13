package com.mindera.presentation.ui.composes.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mindera.presentation.R

@Composable
internal fun FilterBottomActions(
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedButton(onClick = onCancel) { Text(stringResource(R.string.cancel)) }
        Button(onClick = onApply) { Text(stringResource(R.string.apply)) }
    }
}


@Preview
@Composable
private fun FilterBottomActionsPreview(){
    FilterBottomActions(onApply = {}, onCancel = {})
}
