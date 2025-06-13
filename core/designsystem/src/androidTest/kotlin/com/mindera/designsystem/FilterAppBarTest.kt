package com.mindera.designsystem

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterAppBarTest : BaseComposeTest() {

    @Test
    fun filterAppBar_clickFilterIcon_invokesCallback() {
        var clicked = false

        composeTestRule.setContent {
            FilterAppBar(title = "Test Title") {
                clicked = true
            }
        }
        clickNodeWithDescription("Filter")

        assertTrue("Filter icon click should trigger callback", clicked)
    }

    @Test
    fun filterAppBar_Is_Displayed_Correctly() {
        composeTestRule.setContent {
            FilterAppBar("Test Title") { }
        }
        assertTextIsDisplayed("Test Title")
        assertNodeWithDescriptionIsDisplayed("Filter")

    }
}