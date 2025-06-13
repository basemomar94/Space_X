package com.mindera.presentation.ui.composes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mindera.presentation.R
import com.mindera.presentation.models.Link

@Composable
fun LaunchOptionsDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    handleOpenUrl: (Link) -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.open_link)) },
            text = {
                Column {
                    Text(stringResource(R.string.choose_option))
                }
            },
            confirmButton = {
                Row {
                    TextButton(
                        modifier = Modifier.testTag("article_button"),
                        onClick = {
                            handleOpenUrl(Link.ARTICLE)
                            onDismiss()
                        }) { Text(stringResource(R.string.article)) }

                    TextButton(
                        modifier = Modifier.testTag("wikipedia_button"),
                        onClick = {
                            handleOpenUrl(Link.WIKIPEDIA)
                            onDismiss()
                        }) { Text(stringResource(R.string.wikipedia)) }

                    TextButton(
                        modifier = Modifier.testTag("video_button"),
                        onClick = {
                            handleOpenUrl(Link.YOUTUBE)
                            onDismiss()
                        }) { Text(stringResource(R.string.video)) }
                }
            }
        )
    }
}


@Preview
@Composable
private fun LaunchOptionsDialogPreview() {
    MaterialTheme {
        LaunchOptionsDialog(true, {}, {})
    }
}
