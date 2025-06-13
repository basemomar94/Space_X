package com.mindera.presentation.di

import com.mindera.base.BaseMapper
import com.mindera.presentation.mapper.CompanyUiMapper
import com.mindera.presentation.mapper.LaunchUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.mindera.domain.models.Company as CompanyModel
import com.mindera.domain.models.Launch as LaunchModel
import com.mindera.presentation.models.CompanyUi as CompanyUiModel
import com.mindera.presentation.models.LaunchUi as LaunchUiModel


@Module
@InstallIn(SingletonComponent::class)
object UiMapperModule {
    @Provides
    @Singleton
    fun provideCompanyUiMapper(): BaseMapper<CompanyModel, CompanyUiModel> = CompanyUiMapper()

    @Provides
    @Singleton
    fun provideLaunchUiMapper(): BaseMapper<LaunchModel, LaunchUiModel> = LaunchUiMapper()
}
