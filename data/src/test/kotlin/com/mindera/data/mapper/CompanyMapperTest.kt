package com.mindera.data.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.mindera.data.remote.dto.Company as CompanyDto

class CompanyMapperTest {

    private val mapper = CompanyMapper()

    @Test
    fun `map should correctly convert CompanyDto to CompanyModel`() {
        val dto = CompanyDto(
            name = "SpaceX",
            founder = "Elon Musk",
            founded = 2002,
            employees = 8000,
            launch_sites = 3,
            valuation = 100_000_000_000,
            ceo = "bassem",
            coo = "Zayn",
            cto = "Lily",
            cto_propulsion = "",
            headquarters = null,
            links = null,
            summary = "Good company",
            test_sites = 4,
            vehicles = 7
        )

        val model = mapper.map(dto)

        assertEquals("SpaceX", model.name)
        assertEquals("Elon Musk", model.founderName)
        assertEquals(2002, model.foundedYear)
        assertEquals(8000, model.employeesCount)
        assertEquals(3, model.launchSitesCount)
        assertEquals(100_000_000_000, model.valuation)
    }

}