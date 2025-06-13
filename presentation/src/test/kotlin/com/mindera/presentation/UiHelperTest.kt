package com.mindera.presentation

import com.mindera.presentation.models.LaunchUi
import com.mindera.presentation.ui.applyLaunchFilters
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class UiHelperTest {

    @AfterEach
    fun teardown() {
        unmockkAll()
    }

    private val launchData = listOf(
        LaunchUi("Mission1", "2023-01-01", "", "Falcon 1", 100, "", "", "", true, "", false, 2023, 100),
        LaunchUi("Mission2", "2023-01-02", "", "Falcon 2", 150, "", "", "", false, "", false, 2023, 50),
        LaunchUi("Mission3", "2022-01-01", "", "Falcon 3", 300, "", "", "", true, "", false, 2022, 200),
        LaunchUi("Mission4", "2024-01-01", "", "Falcon 4", 10, "", "", "", true, "", false, 2024, null)
    )

    @Test
    fun `applyLaunchFilters returns all launches sorted ascending when no filters applied`() {
        val result = applyLaunchFilters(launchData, years = emptySet(), successOnly = false, asc = true)
        assertEquals(listOf("Mission4", "Mission2", "Mission1", "Mission3"), result.map { it.missionName })
    }

    @Test
    fun `applyLaunchFilters filters by year only`() {
        val result = applyLaunchFilters(launchData, years = setOf(2023), successOnly = false, asc = true)
        assertEquals(listOf("Mission2", "Mission1"), result.map { it.missionName })
    }

    @Test
    fun `applyLaunchFilters filters by success only`() {
        val result = applyLaunchFilters(launchData, years = emptySet(), successOnly = true, asc = true)
        assertEquals(listOf("Mission4", "Mission1", "Mission3"), result.map { it.missionName })
    }

    @Test
    fun `applyLaunchFilters filters by year and success`() {
        val result = applyLaunchFilters(launchData, years = setOf(2023), successOnly = true, asc = true)
        assertEquals(listOf("Mission1"), result.map { it.missionName })
    }

    @Test
    fun `applyLaunchFilters sorts descending`() {
        val result = applyLaunchFilters(launchData, years = emptySet(), successOnly = false, asc = false)
        assertEquals(listOf("Mission3", "Mission1", "Mission2", "Mission4"), result.map { it.missionName })
    }
}
