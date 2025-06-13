package com.mindera.data.repoimp

import com.mindera.base.models.NetworkResult
import com.mindera.data.mapper.LaunchMapper
import com.mindera.data.mapper.mapThrowable
import com.mindera.data.remote.ApiService
import com.mindera.domain.models.Launch
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
import com.mindera.data.remote.dto.Launch as LaunchDto

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchRepoImpTest {

    private val apiService: ApiService = mockk()
    private val mapper: LaunchMapper = mockk()
    private lateinit var repo: LaunchRepoImp

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repo = LaunchRepoImp(apiService, mapper)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getLaunchesList emits Success when API and mapper succeed`() = runTest {
        // Arrange
        val dto1 = mockk<LaunchDto>()
        val dto2 = mockk<LaunchDto>()
        val launch1 = mockk<Launch>()
        val launch2 = mockk<Launch>()

        coEvery { apiService.getAllLaunches() } returns listOf(dto1, dto2)
        every { mapper.map(dto1) } returns launch1
        every { mapper.map(dto2) } returns launch2

        // Act
        val result = repo.getLaunchesList().toList()

        // Assert
        assertEquals(1, result.size)
        val emitted = result[0]
        assertTrue(emitted is NetworkResult.Success)
        assertEquals(listOf(launch1, launch2), (emitted as NetworkResult.Success).data)
    }

    @Test
    fun `getLaunchesList emits Failure when API throws exception`() = runTest {
        // Arrange
        val exception = RuntimeException("API call failed")
        coEvery { apiService.getAllLaunches() } throws exception

        // Act
        val result = repo.getLaunchesList().toList()

        // Assert
        assertEquals(1, result.size)
        val emitted = result[0]
        assertTrue(emitted is NetworkResult.Failure)
        assertEquals(exception.mapThrowable(), (emitted as NetworkResult.Failure).errorTypes)
    }
}
