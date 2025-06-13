package com.mindera.presentation.ui.compose.home

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithTag
import com.mindera.presentation.ui.composes.home.LaunchItem
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.models.LaunchUi
import org.junit.Assert.assertEquals
import org.junit.Test

class LaunchItemTest : BaseComposeTest() {

    private val fakeLaunch = LaunchUi(
        missionName = "FalconSat",
        date = "2022-10-12T18:00:00Z",
        rocketName = "Falcon 1",
        daysSinceLaunch = 800,
        image = "https://example.com/image.jpg",
        articleUrl = "https://example.com/article",
        wikipediaLink = "https://example.com/wiki",
        videoUrl = "https://example.com/video",
        isSuccess = true,
        isInFuture = false,
        time = "",
        year = 2022,
        launchUnix = 1665619200
    )

    @Test
    fun launchItem_displaysCorrect() {
        setTestContent(fakeLaunch)
        assertTextIsDisplayed("Mission: FalconSat")
        assertTextIsDisplayed("Date/Time: 2022-10-12T18:00:00Z")
        assertTextIsDisplayed("Rocket: Falcon 1")
        assertTextIsDisplayed("Days since 800")
    }

    @Test
    fun launchItem_displaysFailureIcon_whenLaunchFails() {
        val failedLaunch = fakeLaunch.copy(isSuccess = false)
        setTestContent(failedLaunch)
        assertTextIsDisplayed("Days since 800")
        assertNodeWithDescriptionIsDisplayed("Failure")
    }

    @Test
    fun launchItem_displaysSuccessIcon_whenLaunchSuccess() {
        val successLaunch = fakeLaunch.copy(isSuccess = true)
        setTestContent(successLaunch)
        assertNodeWithDescriptionIsDisplayed("Success")
    }

    @Test
    fun launchItem_doesNotShowStatusIcon_whenInFuture() {
        val futureLaunch = fakeLaunch.copy(isInFuture = true)
        setTestContent(futureLaunch)
        assertTextIsDisplayed("Days from 800")
        assertTextIsNotDisplayed("Days since 800")
        composeTestRule.onAllNodesWithTag("success_icon").assertCountEquals(0)
        composeTestRule.onAllNodesWithTag("failure_icon").assertCountEquals(0)
    }

    @Test
    fun launchItem_clickTriggersCallbackWithCorrectLinks() {
        var clickedData: Triple<String, String, String>? = null

        setTestContent(fakeLaunch) { wiki, video, article ->
            clickedData = Triple(wiki, video, article)
        }

        clickNodeWithText("Mission: FalconSat")

        assertEquals(fakeLaunch.wikipediaLink, clickedData?.first)
        assertEquals(fakeLaunch.videoUrl, clickedData?.second)
        assertEquals(fakeLaunch.articleUrl, clickedData?.third)
    }

    private fun setTestContent(
        launch: LaunchUi,
        onClick: (String, String, String) -> Unit = { _, _, _ -> }
    ) {
        composeTestRule.setContent {
            LaunchItem(
                launch = launch,
                onClick = onClick
            )
        }
    }
}
