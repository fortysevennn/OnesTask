package com.tugaypamuk.app.onestask.data.remote.dto

import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import com.tugaypamuk.app.onestask.domain.model.AccessLog

data class AccessLogDto(
    val accessDirection: Int?,
    val accessLocation: String?,
    val additionalInfo: String?,
    val computerHash: String?,
    val ipAddress: String?,
    val logID: String,
    val logTime: String?,
    val userID: String?,
    val username: String?,
    val verifyStatusCode: Int?
)
fun AccessLogDto.toAccessLog() : AccessLog{
    return AccessLog(
        accessDirection, accessLocation, additionalInfo, computerHash, ipAddress, logID, logTime, userID, username, verifyStatusCode
    )
}
fun AccessLogDto.toAccessLogEntity() : OnesLogEntitiy{
    return OnesLogEntitiy(
        accessLocation = accessLocation,
        accessDirection = accessDirection,
        additionalInfo = additionalInfo,
        computerHash = computerHash,
        ipAddress = ipAddress,
        logID = logID,
        logTime = logTime,
        userID = userID,
        username = username,
        verifyStatusCode = verifyStatusCode
    )
}