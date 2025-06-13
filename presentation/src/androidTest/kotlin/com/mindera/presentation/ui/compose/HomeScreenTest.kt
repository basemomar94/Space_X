package com.mindera.presentation.ui.compose

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.models.CompanyUi
import com.mindera.presentation.models.LaunchUi
import com.mindera.presentation.ui.composes.HomeCompose
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest : BaseComposeTest() {

    @Test
    fun homeScreen_displaysCompanyAndLaunches() {
        composeTestRule.setContent {
            HomeCompose(
                company = CompanyUi(
                    name = "SpaceX",
                    founderName = "Elon Musk",
                    foundedYear = 2002,
                    employeesCount = 9000,
                    launchSitesCount = 4,
                    valuation = 80000000000
                ),
                launches = listOf(
                    LaunchUi(
                        missionName = "Starlink 24",
                        date = "2025-05-21",
                        rocketName = "Falcon 9",
                        daysSinceLaunch = 2,
                        image = "",
                        articleUrl = "",
                        wikipediaLink = "",
                        videoUrl = "",
                        isSuccess = true,
                        isInFuture = false,
                        year = 2025,
                        time = "",
                        launchUnix = 0
                    )
                )
            )
        }

        assertTextIsDisplayed("Mission: Starlink 24")
        assertTextIsDisplayed("COMPANY")
    }

}