package com.mindera.domain.usecase

import com.mindera.domain.repo.LaunchRepo
import javax.inject.Inject

class FetchLaunchesUseCase @Inject constructor(private val launchRepo: LaunchRepo) {

    operator fun invoke() = launchRepo.getLaunchesList()
}