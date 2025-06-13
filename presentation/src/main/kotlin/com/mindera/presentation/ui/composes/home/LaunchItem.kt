package com.mindera.presentation.ui.composes.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindera.designsystem.HSpacer
import com.mindera.designsystem.ImageCompose
import com.mindera.presentation.models.LaunchUi
import com.mindera.presentation.R

@Composable
internal fun LaunchItem(
    launch: LaunchUi,
    onClick: (String, String, String) -> Unit,
) {
    val isSuccessLaunch = launch.isSuccess
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(launch.wikipediaLink, launch.videoUrl, launch.articleUrl) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            ImageCompose(
                imageUrl = launch.image,
                contentDescription = stringResource(R.string.mission_photo),
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .testTag("launch_image"),
            )
            HSpacer(12.dp)


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.testTag("mission_name"),
                    text = stringResource(R.string.mission_prefix, launch.missionName),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.launch_datetime, launch.date),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.rocket_name, launch.rocketName),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = if (launch.isInFuture) stringResource(R.string.days_from_launch, launch.daysSinceLaunch) else stringResource(R.string.days_since_launch, launch.daysSinceLaunch),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            if (launch.isInFuture == false) {
                Icon(
                    imageVector = if (isSuccessLaunch) Icons.Default.Check else Icons.Default.Clear,
                    contentDescription = stringResource(
                        if (isSuccessLaunch) R.string.success_icon_desc else R.string.failure_icon_desc
                    ),
                    tint = if (isSuccessLaunch) Color(0xFF4CAF50) else Color.Red,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterVertically)
                        .testTag(if (isSuccessLaunch) "success_icon" else "failure_icon")

                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchItemPreview() {
    MaterialTheme {
        LaunchItem(
            launch = LaunchUi(
                missionName = "Starlink 20",
                date = "2025-05-17 14:30 UTC",
                rocketName = "Falcon 9",
                isSuccess = true,
                videoUrl = "",
                time = "",
                daysSinceLaunch = 5,
                wikipediaLink = "dadad",
                image = "",
                articleUrl = "",
                isInFuture = false,
                year = 2025,
                launchUnix = 1621459400
            ),
            onClick = { _, _, _ -> }
        )
    }
}
