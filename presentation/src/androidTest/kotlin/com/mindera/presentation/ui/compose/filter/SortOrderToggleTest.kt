package com.mindera.presentation.ui.compose.filter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.filter.SortOrderToggle
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SortOrderToggleTest : BaseComposeTest() {


    @Test
    fun when_sort_asc_is_true_text_is_displayed_correctly() {
        composeTestRule.setContent {
            SortOrderToggle(isAscending = true, onToggle = {})
        }
        assertTextIsDisplayed("Sort Order")
        assertTextIsDisplayed("ASC")
    }

    @Test
    fun when_sort_asc_is_false_text_is_displayed_correctly() {
        composeTestRule.setContent {
            SortOrderToggle(isAscending = false, onToggle = {})
        }
        assertTextIsDisplayed("Sort Order")
        assertTextIsDisplayed("DESC")
    }
}