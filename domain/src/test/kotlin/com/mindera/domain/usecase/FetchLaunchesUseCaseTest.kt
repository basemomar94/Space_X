package com.mindera.domain.usecase

import com.mindera.base.models.NetworkResult
import com.mindera.domain.models.Launch
import com.mindera.domain.repo.LaunchRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FetchLaunchesUseCaseTest {

    private lateinit var launchRepo: LaunchRepo
    private lateinit var fetchLaunchesUseCase: FetchLaunchesUseCase

    @BeforeEach
    fun setup() {
        launchRepo = mockk()
        fetchLaunchesUseCase = FetchLaunchesUseCase(launchRepo)
        every { launchRepo.getLaunchesList() } returns flowOf(
            NetworkResult.Success(listOf(Launch(null, null, null, null, null, null, null, null)))
        )
    }


    @Test
    fun `invoke should call getLaunchesList on repository`() {
        // Act
        fetchLaunchesUseCase()

        // Assert
        verify(exactly = 1) { launchRepo.getLaunchesList() }
    }
}