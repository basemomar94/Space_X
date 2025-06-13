package com.mindera.presentation.ui.compose.filter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.filter.SuccessFilterSwitch
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SuccessFilterSwitchTest : BaseComposeTest() {

    @Test
    fun successFilterSwitch_initialState_isCheckedCorrectly() {
        composeTestRule.setContent {
            SuccessFilterSwitch(isSuccessOnly = true, onToggle = {})
        }
        assertTextIsDisplayed("Only successful launches")
        assertNodeWithTagOn("success_switch")
    }

    @Test
    fun successFilterSwitch_initialState_isUnCheckedCorrectly() {
        composeTestRule.setContent {
            SuccessFilterSwitch(isSuccessOnly = false, onToggle = {})
        }
        assertTextIsDisplayed("Only successful launches")
        assertNodeWithTagOff("success_switch")
    }

    @Test
    fun successFilterSwitch_toggle_invokesCallbackWithCorrectValue() {
        var newState: Boolean? = null

        composeTestRule.setContent {
            SuccessFilterSwitch(isSuccessOnly = false, onToggle = { newState = it })
        }
        assertTextIsDisplayed("Only successful launches")
        clickNodeWithTag("success_switch")

        assert(newState == true)
    }
}