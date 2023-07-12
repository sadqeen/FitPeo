package com.example.fitpeo.respository

import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.network.ApiService
import com.example.fitpeo.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    @Named("IO") private var dispatcher: CoroutineDispatcher,
    private val apiService: ApiService,
) : BaseApiResponse() {
    suspend fun getDataList(
    ): NetworkResult<List<ResponseData>> {
        return safeApiCallWithErrorData(dispatcher) {
            apiService.getList()
        }
    }
}