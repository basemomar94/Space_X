package com.mindera.presentation.ui.compose.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.models.CompanyUi
import com.mindera.presentation.models.LaunchUi
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.HomeCompose
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeComposeTest : BaseComposeTest() {

    private val fakeCompany = CompanyUi(
        name = "SpaceX",
        founderName = "Elon Musk",
        foundedYear = 2002,
        employeesCount = 10000,
        launchSitesCount = 3,
        valuation = 74000000000L
    )

    private val fakeLaunches = listOf(
        LaunchUi(
            missionName = "Test Mission",
            date = "2025-01-01",
            rocketName = "Falcon 9",
            daysSinceLaunch = 100,
            image = "",
            articleUrl = "https://example.com/article",
            wikipediaLink = "https://example.com/wiki",
            videoUrl = "https://example.com/video",
            isSuccess = true,
            isInFuture = false,
            year = 2025,
            time = "",
            launchUnix = 0
        )
    )

    @Test
    fun homeCompose_showsCompanyAndLaunches() {
        setTestContent()
        assertTextIsDisplayed("Mission: Test Mission")
        assertTextIsDisplayed("COMPANY")
    }

    @Test
    fun homeCompose_clickLaunchItem_showsDialog() {
       setTestContent()

        clickNodeWithText("Mission: Test Mission")

        assertTextIsDisplayed("Choose an option to open")
        assertTextIsDisplayed("Article")
        assertTextIsDisplayed("Wikipedia")
        assertTextIsDisplayed("Video")
    }

    @Test
    fun homeCompose_openAndCloseFilterSheet() {
       setTestContent()

        clickNodeWithDescription("Filter")

        assertTextIsDisplayed("Filter Launches")

        clickNodeWithText("Cancel")
        composeTestRule.waitForIdle()
        assertTextIsNotDisplayed("Filter Launches")
    }

    private fun setTestContent() {
        composeTestRule.setContent {
            HomeCompose(
                company = fakeCompany,
                launches = fakeLaunches
            )
        }
    }
}
