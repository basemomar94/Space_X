package com.mindera.data.repoimp

import android.os.Build
import androidx.annotation.RequiresExtension
import com.mindera.base.models.NetworkResult
import com.mindera.data.mapper.CompanyMapper
import com.mindera.data.mapper.mapThrowable
import com.mindera.data.remote.ApiService
import com.mindera.domain.models.Company
import com.mindera.domain.repo.CompanyRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class CompanyRepoImp @Inject constructor(
    private val apiService: ApiService,
    private val mapper: CompanyMapper,
) :
    CompanyRepo {
    override fun getCompanyInfo(): Flow<NetworkResult<Company>> {
        return flow<NetworkResult<Company>> {
            val result = mapper.map(apiService.getCompanyInfo())
            emit(NetworkResult.Success(result))
        }.catch { e ->
            val mappedError = e.mapThrowable()
            emit(NetworkResult.Failure(mappedError))
        }.flowOn(Dispatchers.IO)
    }

}