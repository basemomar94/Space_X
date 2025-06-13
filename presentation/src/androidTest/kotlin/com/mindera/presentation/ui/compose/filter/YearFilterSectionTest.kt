package com.mindera.presentation.ui.compose.filter

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.filter.YearFilterSection
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class YearFilterSectionTest : BaseComposeTest() {
    private val testYears = listOf(2022, 2023, 2024)
    private var clickedYear: Int? = null


    @Test
    fun yearFilterSection_displaysChipsInDescendingOrder() {
        composeTestRule.setContent {
            YearFilterSection(testYears, setOf(2022, 2023)) {}
        }
        testYears.sortedDescending().forEach { year ->
            assertNodeWithTagIsDisplayed("chip_$year")
        }
    }

    @Test
    fun yearFilterSection_clickOnChip_triggersCallback() {
        composeTestRule.setContent {
            YearFilterSection(testYears, setOf(2022, 2023)) {
                clickedYear = it
            }
        }
        clickNodeWithTag("chip_2022")
        assertEquals(2022, clickedYear)
    }

    @Test
    fun yearFilterSection_selectedChip_isReflected() {
        composeTestRule.setContent {
            YearFilterSection(
                years = testYears,
                selected = setOf(2024),
                onYearToggle = {}
            )
        }
        assertNodeWithTagSelected("chip_2024")
        assertNodeWithTagNotSelected("chip_2022")
    }
}