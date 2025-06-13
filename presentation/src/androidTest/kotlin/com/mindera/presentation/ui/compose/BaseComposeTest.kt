package com.mindera.presentation.ui.compose


import android.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
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

    fun clickNodeWithTag(text: String) {
        composeTestRule.onNodeWithTag(text).performClick()
    }

    fun clickNodeWithDescription(text: String) {
        composeTestRule.onNodeWithContentDescription(text).performClick()
    }

    fun assertColorOnNodeWithText(text: String, color: Color) {
        composeTestRule.onNodeWithText(text).assertBackgroundColor(color)
    }

    fun assertNodeWithTagIsDisplayed(tag: String) {
        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()

    }

    fun assertNodeWithTagSelected(tag: String) {
        composeTestRule.onNodeWithTag(tag).assertIsSelected()
    }
    fun assertNodeWithTagNotSelected(tag: String) {
        composeTestRule.onNodeWithTag(tag).assertIsNotSelected()
    }

    fun assertNodeWithTagOn(tag: String) {
        composeTestRule.onNodeWithTag(tag).assertIsOn()
    }
    fun assertNodeWithTagOff(tag: String) {
        composeTestRule.onNodeWithTag(tag).assertIsOff()
    }

    private fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
        val capturedName = captureToImage().colorSpace.name
        assertEquals(expectedBackground.colorSpace.name, capturedName)
    }
}