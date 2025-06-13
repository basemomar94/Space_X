package com.mindera.presentation.ui.composes.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindera.designsystem.VSpacer
import com.mindera.presentation.R

@Composable
internal fun CompanyCard(
    companyName: String,
    founderName: String,
    year: Int,
    employees: Int,
    launchSites: Int,
    valuation: Long,
) {
    val companyText = stringResource(
        id = R.string.company_info,
        companyName,
        founderName,
        year,
        employees,
        launchSites,
        valuation
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("company_card"),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.company),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp)
                    .testTag("company_title")
            )
            VSpacer()
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("company_info_text"),
                text = companyText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CompanyCardPreview() {
    MaterialTheme {
        CompanyCard(
            companyName = "We are the best",
            founderName = "bassem",
            year = 2025,
            employees = 25,
            launchSites = 5,
            valuation = 50000
        )
    }

}
