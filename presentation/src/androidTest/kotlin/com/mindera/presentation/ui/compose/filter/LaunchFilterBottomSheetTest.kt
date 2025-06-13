package com.mindera.presentation.ui.compose.filter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mindera.presentation.models.Filter
import com.mindera.presentation.ui.compose.BaseComposeTest
import com.mindera.presentation.ui.composes.filter.LaunchFilterBottomSheet
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LaunchFilterBottomSheetTest : BaseComposeTest() {

    private val allYears = listOf(2022, 2023, 2024)

    @Test
    fun launchFilterBottomSheet_rendersAllSections() {
        setTestContent()

       assertTextIsDisplayed("Filter Launches")
        allYears.forEach { year ->
            assertNodeWithTagIsDisplayed("chip_$year")
        }
        assertNodeWithTagIsDisplayed("success_switch")
        assertTextIsDisplayed("Apply")
        assertTextIsDisplayed("Cancel")
    }

    @Test
    fun launchFilterBottomSheet_toggleYear_updatesSelection() {
        var resultFilter: Filter? = null

        setTestContent(onApply = { resultFilter = it })

        clickNodeWithTag("chip_2024")

        clickNodeWithText("Apply")

        assertEquals(setOf(2024), resultFilter?.years)
    }

    @Test
    fun launchFilterBottomSheet_toggleSuccessSwitch_updatesState() {
        var resultFilter: Filter? = null

        setTestContent(
            selectedYears = emptySet(),
            isSuccessOnly = false,
            onApply = { resultFilter = it }
        )

        clickNodeWithTag("success_switch")
        clickNodeWithText("Apply")

        assertEquals(true, resultFilter?.launchSuccess)
    }

    @Test
    fun launchFilterBottomSheet_toggleSortOrder_updatesState() {
        var resultFilter: Filter? = null

        setTestContent(
            selectedYears = emptySet(),
            isSuccessOnly = false,
            isAscending = false,
            onApply = { resultFilter = it }
        )

        clickNodeWithText("DESC")
        clickNodeWithText("Apply")

        assertEquals(true, resultFilter?.isAscending)
    }

    @Test
    fun launchFilterBottomSheet_cancelClick_invokesDismiss() {
        var dismissed = false

        setTestContent(onDismiss = { dismissed = true })

        clickNodeWithText("Cancel")

        assertEquals(true, dismissed)
    }

    private fun setTestContent(
        selectedYears: Set<Int> = emptySet(),
        isSuccessOnly: Boolean = false,
        isAscending: Boolean = false,
        onApply: (Filter) -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            LaunchFilterBottomSheet(
                allYears = allYears,
                selectedYears = selectedYears,
                isSuccessOnly = isSuccessOnly,
                isAscending = isAscending,
                onApply = onApply,
                onDismiss = onDismiss
            )
        }
    }
}
