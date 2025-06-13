package com.mindera.domain.repo

import com.mindera.base.models.NetworkResult
import com.mindera.domain.models.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepo {
     fun getCompanyInfo(): Flow<NetworkResult<Company>>

}