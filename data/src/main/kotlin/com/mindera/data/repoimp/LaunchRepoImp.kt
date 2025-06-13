package com.mindera.data.repoimp

import android.os.Build
import androidx.annotation.RequiresExtension
import com.mindera.base.models.NetworkResult
import com.mindera.base.utils.Logger
import com.mindera.data.mapper.LaunchMapper
import com.mindera.data.mapper.mapThrowable
import com.mindera.data.remote.ApiService
import com.mindera.domain.models.Launch
import com.mindera.domain.repo.LaunchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class LaunchRepoImp @Inject constructor(
    private val apiService: ApiService,
    private val mapper: LaunchMapper,
) : LaunchRepo {
    val logger = Logger("-- LaunchRepoImp")

    override fun getLaunchesList(): Flow<NetworkResult<List<Launch>>> {
        return flow<NetworkResult<List<Launch>>> {
            val result = apiService.getAllLaunches().map { mapper.map(it) }
            emit(NetworkResult.Success(result))
        }.catch { e ->
            logger.e(e.message.toString())
            val mappedError = e.mapThrowable()
            emit(NetworkResult.Failure(mappedError))
        }.flowOn(Dispatchers.IO)
    }
}