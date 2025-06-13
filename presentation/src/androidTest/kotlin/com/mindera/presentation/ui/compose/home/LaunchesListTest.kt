package com.mindera.presentation.ui.compose.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.models.LaunchUi
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.home.LaunchesList
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchesListTest : BaseComposeTest() {

    private val fakeLaunches = listOf(
        LaunchUi(
            missionName = "Falcon 1",
            date = "2022-01-01T00:00:00Z",
            rocketName = "Falcon",
            daysSinceLaunch = 500,
            image = "",
            articleUrl = "https://example.com/article1",
            wikipediaLink = "https://example.com/wiki1",
            videoUrl = "https://example.com/video1",
            isSuccess = true,
            isInFuture = false,
            year = 2022,
            time = "",
            launchUnix = 205656565
        ),
        LaunchUi(
            missionName = "Falcon 2",
            date = "2023-01-01T00:00:00Z",
            rocketName = "Falcon Heavy",
            daysSinceLaunch = 300,
            image = "",
            articleUrl = "https://example.com/article2",
            wikipediaLink = "https://example.com/wiki2",
            videoUrl = "https://example.com/video2",
            isSuccess = false,
            isInFuture = false,
            year = 2023,
            time = "",
            launchUnix = 26545454
        )
    )

    @Test
    fun launchesList_rendersAllItems() {
        setTestContent(fakeLaunches)

        assertTextIsDisplayed("Mission: Falcon 1")
        assertTextIsDisplayed("Mission: Falcon 2")
    }

    @Test
    fun launchesList_clicksOnItemTriggersCallback() {
        var clickedResult: Triple<String, String, String>? = null

        setTestContent(fakeLaunches) { wiki, video, article ->
            clickedResult = Triple(wiki, video, article)
        }

        clickNodeWithText("Mission: Falcon 2")

        assertEquals("https://example.com/wiki2", clickedResult?.first)
        assertEquals("https://example.com/video2", clickedResult?.second)
        assertEquals("https://example.com/article2", clickedResult?.third)
    }

    private fun setTestContent(
        launches: List<LaunchUi>,
        onClick: (String, String, String) -> Unit = { _, _, _ -> }
    ) {
        composeTestRule.setContent {
            LaunchesList(launchesList = launches, onClick = onClick)
        }
    }
}
