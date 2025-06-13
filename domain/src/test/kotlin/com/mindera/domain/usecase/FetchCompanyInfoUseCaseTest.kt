package com.mindera.domain.usecase

import com.mindera.base.models.NetworkResult
import com.mindera.domain.models.Company
import com.mindera.domain.repo.CompanyRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FetchCompanyInfoUseCaseTest {

    private lateinit var companyRepo: CompanyRepo
    private lateinit var fetchCompanyUseCase: FetchCompanyInfoUseCase

    @BeforeEach
    fun setup() {
        companyRepo = mockk()
        fetchCompanyUseCase = FetchCompanyInfoUseCase(companyRepo)
        every { companyRepo.getCompanyInfo() } returns flowOf(
            NetworkResult.Success(Company(null, null, null, null, null, null))
        )
    }


    @Test
    fun `invoke should call getLaunchesList on repository`() {
        // Act
        fetchCompanyUseCase()

        // Assert
        verify(exactly = 1) { companyRepo.getCompanyInfo() }
    }
}