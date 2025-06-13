package com.mindera.presentation

import app.cash.turbine.test
import com.mindera.base.models.ErrorTypes
import com.mindera.base.models.NetworkResult
import com.mindera.domain.models.Company
import com.mindera.domain.models.Launch
import com.mindera.domain.usecase.FetchCompanyInfoUseCase
import com.mindera.domain.usecase.FetchLaunchesUseCase
import com.mindera.presentation.mapper.CompanyUiMapper
import com.mindera.presentation.mapper.LaunchUiMapper
import com.mindera.presentation.models.CompanyUi
import com.mindera.presentation.models.LaunchUi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {


    private val fetchCompanyInfoUseCase: FetchCompanyInfoUseCase = mockk()
    private val fetchLaunchesUseCase: FetchLaunchesUseCase = mockk()
    private val companyUiMapper: CompanyUiMapper = mockk()
    private val launchUiMapper: LaunchUiMapper = mockk()

    private lateinit var viewModel: HomeViewModel
    private val fakeCompany = Company(
        name = "SpaceX",
        valuation = 100000000000,
        founderName = "Elon Musk",
        employeesCount = 100,
        launchSitesCount = 5,
        foundedYear = 2002
    )
    private val fakeLaunch = Launch(
        missionName = "Falcon 9",
        rocketName = "Falcon 9",
        launchUnix = 1234567890,
        videoUrl = "https://example.com/video",
        articleUrl = "https://example.com/article",
        wikipediaLink = "https://example.com/wikipedia",
        isSuccess = true,
        image = "https://example.com/image.jpg"
    )

    private val fakeCompanyUi = CompanyUi(
        founderName = "Elon Musk",
        name = "SpaceX",
        valuation = 100000000000,
        employeesCount = 100,
        launchSitesCount = 5,
        foundedYear = 2002
    )
    private val fakeLaunchUi = LaunchUi(
        missionName = "Falcon 9",
        date = "2021-01-01",
        time = "12:00:00",
        rocketName = "Falcon 9",
        daysSinceLaunch = 10,
        videoUrl = "https://example.com/video",
        articleUrl = "https://example.com/article",
        wikipediaLink = "https://example.com/wikipedia",
        isSuccess = true,
        image = "https://example.com/image.jpg",
        isInFuture = false,
        year = 2021,
        launchUnix = 1234567890
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `when both use cases return Success, then state is Success and no error effect`() =
        runTest {

            coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Success(fakeCompany))
            coEvery { fetchLaunchesUseCase() } returns flowOf(
                NetworkResult.Success(
                    listOf(
                        fakeLaunch
                    )
                )
            )
            every { companyUiMapper.map(fakeCompany) } returns fakeCompanyUi
            every { launchUiMapper.map(fakeLaunch) } returns fakeLaunchUi

            viewModel = HomeViewModel(
                fetchCompanyInfoUseCase,
                fetchLaunchesUseCase,
                companyUiMapper,
                launchUiMapper
            )

            val expectedState = HomeState.Success(
                companyUi = fakeCompanyUi,
                launch = listOf(fakeLaunchUi)
            )

            viewModel.viewState.test {
                assertEquals(HomeState.Loading, awaitItem())
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.effect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `when company use case fails and launches succeed, then emits error effect`() = runTest {
        // Given
        val fakeError = ErrorTypes.Unknown("Something went wrong")

        coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Failure(fakeError))
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Success(listOf(fakeLaunch)))
        every { launchUiMapper.map(fakeLaunch) } returns fakeLaunchUi

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            // No Success state should be emitted because company is null
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(fakeError, (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when company use case succeeds and launches fail, then emits error effect`() = runTest {
        // Given
        val fakeError = ErrorTypes.Unknown("Launches retrieval failed")

        coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Success(fakeCompany))
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Failure(fakeError))
        every { companyUiMapper.map(fakeCompany) } returns fakeCompanyUi

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            // No success state should be emitted
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(fakeError, (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }
    @Test
    fun `when both use cases fail, then emits error effect with correct error type`() = runTest {
        // Given
        val companyError = ErrorTypes.Unknown("Company API down")
        val launchError = ErrorTypes.Unknown("Launch API bad request")

        coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Failure(companyError))
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Failure(launchError))

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(companyError, (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when both use cases return Success with null data, then emits unknown error effect`() = runTest {
        // Given
        coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Success(null))
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Success(null))

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            // Should not emit Success state
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(ErrorTypes.Unknown(null), (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when company use case returns Success with null and launches succeed, then emits unknown error effect`() = runTest {
        // Given
        coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Success(null))
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Success(listOf(fakeLaunch)))
        every { launchUiMapper.map(fakeLaunch) } returns fakeLaunchUi

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(ErrorTypes.Unknown(null), (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when company use case succeeds and launches return Success with null, then emits unknown error effect`() = runTest {
        // Given
        coEvery { fetchCompanyInfoUseCase() } returns flowOf(NetworkResult.Success(fakeCompany))
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Success(null))
        every { companyUiMapper.map(fakeCompany) } returns fakeCompanyUi

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(ErrorTypes.Unknown(null), (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when use case throws exception, then emits unknown error effect`() = runTest {
        // Given
        coEvery { fetchCompanyInfoUseCase() } returns flow {
            throw RuntimeException("Unexpected failure")
        }
        coEvery { fetchLaunchesUseCase() } returns flowOf(NetworkResult.Success(listOf(fakeLaunch)))
        every { launchUiMapper.map(fakeLaunch) } returns fakeLaunchUi

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(ErrorTypes.Unknown(null), (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when initial load fails and LoadData is triggered, emits success after retry`() = runTest {
        // Given
        coEvery { fetchCompanyInfoUseCase() } returnsMany listOf(
            flowOf(NetworkResult.Failure(ErrorTypes.Unknown("company fail"))),
            flowOf(NetworkResult.Success(fakeCompany))
        )
        coEvery { fetchLaunchesUseCase() } returnsMany listOf(
            flowOf(NetworkResult.Failure(ErrorTypes.Unknown("launch fail"))),
            flowOf(NetworkResult.Success(listOf(fakeLaunch)))
        )
        every { companyUiMapper.map(fakeCompany) } returns fakeCompanyUi
        every { launchUiMapper.map(fakeLaunch) } returns fakeLaunchUi

        // When
        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Give time for first loadData to complete
        advanceUntilIdle()

        // Retry
        viewModel.setEvent(HomeIntent.LoadData)

        // Give time for retry
        advanceUntilIdle()

        // Then
        viewModel.viewState.test {
            assertEquals(HomeState.Loading, awaitItem()) // initial
            // no Success because failure didn't emit state
            assertEquals(HomeState.Success(fakeCompanyUi, listOf(fakeLaunchUi)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
    @Test
    fun `when flow emits NetworkResult_Failure, then resultFailure is triggered and emits error effect`() = runTest {
        // Given
        val failure = NetworkResult.Failure(ErrorTypes.Unknown("fail"))
        val success = NetworkResult.Success(listOf(fakeLaunch))

        coEvery { fetchCompanyInfoUseCase() } returns flowOf(failure)
        coEvery { fetchLaunchesUseCase() } returns flowOf(success)
        every { launchUiMapper.map(fakeLaunch) } returns fakeLaunchUi

        viewModel = HomeViewModel(
            fetchCompanyInfoUseCase,
            fetchLaunchesUseCase,
            companyUiMapper,
            launchUiMapper
        )

        // Then
        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeEffect.Error)
            assertEquals(ErrorTypes.Unknown("fail"), (effect as HomeEffect.Error).errorTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }








}
