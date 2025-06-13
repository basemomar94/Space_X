package com.mindera.data.repoimp

import com.mindera.base.models.NetworkResult
import com.mindera.data.mapper.CompanyMapper
import com.mindera.data.mapper.mapThrowable
import com.mindera.data.remote.ApiService
import com.mindera.domain.models.Company
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.mindera.data.remote.dto.Company as CompanyDto

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyRepoImpTest {

    private val apiService: ApiService = mockk()
    private val mapper: CompanyMapper = mockk()
    private lateinit var companyRepo: CompanyRepoImp

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        companyRepo = CompanyRepoImp(apiService, mapper)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getCompanyInfo emits Success when API and mapper succeed`() = runTest {
        // Arrange
        val dto = mockk<CompanyDto>()
        val company = mockk<Company>()

        coEvery { apiService.getCompanyInfo() } returns dto
        every { mapper.map(dto) } returns company

        // Act
        val result = companyRepo.getCompanyInfo().toList()

        // Assert
        assertEquals(1, result.size)
        val emitted = result[0]
        assertTrue(emitted is NetworkResult.Success)
        assertEquals(company, (emitted as NetworkResult.Success).data)
    }

    @Test
    fun `getCompanyInfo emits Failure when API throws exception`() = runTest {
        // Arrange
        val exception = RuntimeException("API call failed")
        coEvery { apiService.getCompanyInfo() } throws exception

        // Act
        val result = companyRepo.getCompanyInfo().toList()

        // Assert
        assertEquals(1, result.size)
        val emitted = result[0]
        assertTrue(emitted is NetworkResult.Failure)
        assertEquals(exception.mapThrowable(), (emitted as NetworkResult.Failure).errorTypes)
    }
}

