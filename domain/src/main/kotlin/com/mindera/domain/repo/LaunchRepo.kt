package com.mindera.domain.repo

import com.mindera.base.models.NetworkResult
import com.mindera.domain.models.Launch
import kotlinx.coroutines.flow.Flow

interface LaunchRepo {
     fun getLaunchesList(): Flow<NetworkResult<List<Launch>>>

}