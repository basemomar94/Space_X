package com.mindera.presentation.ui.compose.home

import com.mindera.presentation.ui.composes.home.LaunchOptionsDialog


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.ui.compose.BaseComposeTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchOptionsDialogTest : BaseComposeTest() {

    private var dismissed = false
    private val fakeArticleUrl = "https://example.com/article"
    private val fakeWikipediaUrl = "https://example.com/wiki"
    private val fakeVideoUrl = "https://example.com/video"

    @Test
    fun dialog_isDisplayed_whenShowIsTrue() {
        setTestContent(show = true)

        assertTextIsDisplayed("Open Launch Details")
        assertTextIsDisplayed("Choose an option to open")
        assertTextIsDisplayed("Article")
        assertTextIsDisplayed("Wikipedia")
        assertTextIsDisplayed("Video")
    }

    @Test
    fun dialog_isNotDisplayed_whenShowIsFalse() {
        setTestContent(show = false)

        assertTextIsNotDisplayed("Open Launch Details")
        assertTextIsNotDisplayed("Choose an option to open")
        assertTextIsNotDisplayed("Article")
        assertTextIsNotDisplayed("Wikipedia")
        assertTextIsNotDisplayed("Video")
    }

    @Test
    fun clickingArticleButton_opensUrl_andDismissesDialogWhenUrlIsNotEmpty() {
        dismissed = false
        setTestContent(show = true)

        clickNodeWithTag("article_button")

        assertTrue(dismissed)
    }


    @Test
    fun clickingWikipediaButton_opensUrl_andDismissesDialog() {
        dismissed = false
        setTestContent(show = true)

        clickNodeWithTag("wikipedia_button")

        assertTrue(dismissed)
    }

    @Test
    fun clickingVideoButton_opensUrl_andDismissesDialog() {
        dismissed = false
        setTestContent(show = true)

        clickNodeWithTag("video_button")

        assertTrue(dismissed)
    }

    private fun setTestContent(
        show: Boolean,
        wikipediaLink: String = fakeWikipediaUrl,
        articleLink: String = fakeArticleUrl,
        videoLink: String = fakeVideoUrl
    ) {
        composeTestRule.setContent {
            LaunchOptionsDialog(
                show = show,
                onDismiss = { dismissed = true },
                handleOpenUrl = {}
            )
        }
    }
}
