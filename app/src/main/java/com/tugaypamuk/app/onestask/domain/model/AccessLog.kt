package com.tugaypamuk.app.onestask.domain.model

data class AccessLog(
    val accessDirection: Int?,
    val accessLocation: String?,
    val additionalInfo: String?,
    val computerHash: String?,
    val ipAddress: String?,
    val logID: String?,
    val logTime: String?,
    val userID: String?,
    val username: String?,
    val verifyStatusCode: Int?
)
