package com.tugaypamuk.app.onestask.data.repository

import com.tugaypamuk.app.onestask.data.local.dao.OnesLogDao
import com.tugaypamuk.app.onestask.data.local.dao.OnesMailDao
import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import com.tugaypamuk.app.onestask.data.remote.api.OnesApi
import com.tugaypamuk.app.onestask.data.remote.dto.AccessLogDto
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class OnesRepositoryImpl @Inject constructor(
    private val api : OnesApi,
    private val logDao : OnesLogDao,
    private val mailDao: OnesMailDao
)  : OnesRepository{
    override suspend fun getAccessLog(): AccessLogDto  {
        return api.getAccessLog()
    }

    override suspend fun approveAccessLog(parameters : HashMap<String,String>): Response<Void> {
        return api.approveAccessLog(parameters)
    }

    override suspend fun setAccessLog(onesLogEntitiy: OnesLogEntitiy) {
        return logDao.addLogs(onesLogEntitiy)
    }

    override suspend fun getAccessLogFromDatabase(): Flow<List<OnesLogEntitiy>> {
        return logDao.getAllLogs()
    }

    override suspend fun getMailSettings(): Flow<MailSettingsEntity?> {
        return mailDao.getMailSettings()
    }

    override suspend fun addMailSettings(mailSettingsEntity: MailSettingsEntity) {
        return mailDao.addMailSettings(mailSettingsEntity)
    }
}