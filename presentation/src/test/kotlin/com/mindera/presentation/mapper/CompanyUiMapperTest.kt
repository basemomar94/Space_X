package com.mindera.presentation.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.mindera.domain.models.Company as CompanyModel
import com.mindera.presentation.models.CompanyUi as CompanyUi
class CompanyUiMapperTest {

    private val mapper = CompanyUiMapper()

    @Test
    fun `maps all non-null fields correctly`() {
        // Given
        val model = CompanyModel(
            name = "SpaceX",
            founderName = "Elon Musk",
            foundedYear = 2002,
            employeesCount = 9000,
            launchSitesCount = 5,
            valuation = 100_000_000_000L
        )

        // When
        val result = mapper.map(model)

        // Then
        assertEquals("SpaceX", result.name)
        assertEquals("Elon Musk", result.founderName)
        assertEquals(2002, result.foundedYear)
        assertEquals(9000, result.employeesCount)
        assertEquals(5, result.launchSitesCount)
        assertEquals(100_000_000_000L, result.valuation)
    }

    @Test
    fun `maps null fields to default values`() {
        // Given
        val model = CompanyModel(
            name = null,
            founderName = null,
            foundedYear = null,
            employeesCount = null,
            launchSitesCount = null,
            valuation = null
        )

        // When
        val result = mapper.map(model)

        // Then
        assertEquals("Unknown", result.name)
        assertEquals("Unknown", result.founderName)
        assertEquals(-1, result.foundedYear)
        assertEquals(-1, result.employeesCount)
        assertEquals(-1, result.launchSitesCount)
        assertEquals(0L, result.valuation)
    }
}
