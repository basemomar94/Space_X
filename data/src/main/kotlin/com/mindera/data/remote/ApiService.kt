package com.mindera.data.remote


import com.mindera.data.remote.dto.Company
import com.mindera.data.remote.dto.Launch
import retrofit2.http.GET

interface ApiService {

    @GET("v3/info")
    suspend fun getCompanyInfo(): Company

    @GET("v3/launches")
    suspend fun getAllLaunches(): List<Launch>
}
