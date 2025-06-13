package com.mindera.presentation.ui.compose.filter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.filter.FilterBottomActions
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterBottomActionsTest : BaseComposeTest() {


    @Test
    fun filter_bottom_actions_are_displayed_correctly() {
        composeTestRule.setContent {
            FilterBottomActions({}, {})
        }
        assertTextIsDisplayed("Cancel")
        assertTextIsDisplayed("Apply")
    }
}