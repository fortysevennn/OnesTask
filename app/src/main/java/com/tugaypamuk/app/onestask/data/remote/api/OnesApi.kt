package com.tugaypamuk.app.onestask.data.remote.api

import com.tugaypamuk.app.onestask.data.remote.dto.AccessLogDto
import com.tugaypamuk.app.onestask.data.remote.dto.BaseResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OnesApi {

    @GET("API/AccessLog")
    suspend fun getAccessLog(
        @Header("Content-Type") contentType: String = "application/json; charset=utf-8"
    ) : AccessLogDto

    @POST("/API/AccessLog")
    suspend fun approveAccessLog(
        @Body parameters : HashMap<String,String>,
        @Header("Content-Type") contentType: String = "application/json; charset=utf-8"
    ) : Response<Void>
}
