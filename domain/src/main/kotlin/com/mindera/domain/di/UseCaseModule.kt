package com.mindera.domain.di

import com.mindera.domain.repo.CompanyRepo
import com.mindera.domain.repo.LaunchRepo
import com.mindera.domain.usecase.FetchCompanyInfoUseCase
import com.mindera.domain.usecase.FetchLaunchesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchCompanyInfoUseCase(companyRepo: CompanyRepo) =
        FetchCompanyInfoUseCase(companyRepo)

    @Provides
    fun provideFetchLaunchesUseCase(launchRepo: LaunchRepo) =
        FetchLaunchesUseCase(launchRepo)
}