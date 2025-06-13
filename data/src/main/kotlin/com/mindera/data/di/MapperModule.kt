package com.mindera.data.di

import com.mindera.base.BaseMapper
import com.mindera.data.mapper.CompanyMapper
import com.mindera.data.mapper.LaunchMapper
import com.mindera.data.remote.dto.Company
import com.mindera.data.remote.dto.Launch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    @Singleton
    fun provideCompanyMapper(): BaseMapper<Company, com.mindera.domain.models.Company> {
        return CompanyMapper()
    }

    @Provides
    @Singleton
    fun provideLaunchMapper(): BaseMapper<Launch, com.mindera.domain.models.Launch> {
        return LaunchMapper()
    }


}