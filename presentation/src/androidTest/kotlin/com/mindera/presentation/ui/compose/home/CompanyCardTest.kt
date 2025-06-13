package com.mindera.presentation.ui.compose.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.home.CompanyCard
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompanyCardTest : BaseComposeTest() {

    @Test
    fun companyCard_displaysTitleAndInfo() {
        composeTestRule.setContent {
            CompanyCard(
                companyName = "SpaceX",
                founderName = "Elon Musk",
                year = 2002,
                employees = 7000,
                launchSites = 3,
                valuation = 74000000000L
            )
        }

        assertNodeWithTagIsDisplayed("company_title")
        assertTextIsDisplayed("COMPANY")

        assertNodeWithTagIsDisplayed("company_info_text")
        val expectedText = "SpaceX was founded by Elon Musk in 2002. It has now 7000 employees, 3 launch sites, and is valued at USD 74000000000."
        assertTextIsDisplayed(expectedText)
    }
}