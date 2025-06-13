package com.mindera.data.di

import com.mindera.data.repoimp.CompanyRepoImp
import com.mindera.data.repoimp.LaunchRepoImp
import com.mindera.domain.repo.CompanyRepo
import com.mindera.domain.repo.LaunchRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindCompanyRepo(impl: CompanyRepoImp): CompanyRepo

    @Binds
    abstract fun bindLaunchRepo(impl: LaunchRepoImp): LaunchRepo
}