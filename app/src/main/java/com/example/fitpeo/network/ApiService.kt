package com.example.fitpeo.network

import com.example.fitpeo.model.ResponseData
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    suspend fun getList(
    ): List<ResponseData>


}