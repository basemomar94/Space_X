package com.mindera.designsystem

import android.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule

abstract class BaseComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    open fun setUp() {

    }

    fun assertTextIsDisplayed(text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    fun assertTextIsNotDisplayed(text: String) {
        composeTestRule.onNodeWithText(text).assertIsNotDisplayed()
    }

    fun assertNodeWithDescriptionIsDisplayed(text: String) {
        composeTestRule.onNodeWithContentDescription(text).assertIsDisplayed()
    }


    fun clickNodeWithText(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }

    fun clickNodeWithDescription(text: String) {
        composeTestRule.onNodeWithContentDescription(text).performClick()
    }

    fun assertColorOnNodeWithText(text: String, color: Color) {
        composeTestRule.onNodeWithText(text).assertBackgroundColor(color)
    }

    private fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
        val capturedName = captureToImage().colorSpace.name
        assertEquals(expectedBackground.colorSpace.name, capturedName)
    }
}