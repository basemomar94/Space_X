package com.mindera.presentation.ui.composes.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mindera.presentation.R

@Composable
internal fun SuccessFilterSwitch(
    isSuccessOnly: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.only_success_launches), modifier = Modifier.weight(1f))
        Switch(modifier = Modifier.testTag("success_switch"), checked = isSuccessOnly, onCheckedChange = onToggle)
    }
}


@Preview(showBackground = true)
@Composable
private fun SuccessFilterSwitchPreview() {
    SuccessFilterSwitch(isSuccessOnly = true, onToggle = {})
}
