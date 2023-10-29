package com.tugaypamuk.app.onestask.domain.repository

import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import com.tugaypamuk.app.onestask.data.remote.dto.AccessLogDto
import com.tugaypamuk.app.onestask.data.remote.dto.BaseResult
import com.tugaypamuk.app.onestask.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface OnesRepository {

    suspend fun getAccessLog() : AccessLogDto
    suspend fun approveAccessLog(parameters : HashMap<String,String>) : Response<Void>


    suspend fun setAccessLog(onesLogEntitiy: OnesLogEntitiy)
    suspend fun getAccessLogFromDatabase() : Flow<List<OnesLogEntitiy>>

    suspend fun getMailSettings() : Flow<MailSettingsEntity?>
    suspend fun addMailSettings(mailSettingsEntity: MailSettingsEntity)
}