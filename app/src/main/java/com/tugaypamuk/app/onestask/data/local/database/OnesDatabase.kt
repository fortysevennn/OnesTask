package com.tugaypamuk.app.onestask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tugaypamuk.app.onestask.data.local.dao.OnesLogDao
import com.tugaypamuk.app.onestask.data.local.dao.OnesMailDao
import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy


@Database(
    entities = [OnesLogEntitiy::class,MailSettingsEntity::class],
    version = 1
)

abstract class OnesDatabase  : RoomDatabase(){
    abstract val onesLogDao : OnesLogDao
    abstract val onesMailDao : OnesMailDao
}