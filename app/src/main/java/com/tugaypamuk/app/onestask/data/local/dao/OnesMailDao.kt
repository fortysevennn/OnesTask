package com.tugaypamuk.app.onestask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OnesMailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMailSettings(mailServer : MailSettingsEntity?)

    @Query("SELECT * FROM ones_mail")
    fun getMailSettings() : Flow<MailSettingsEntity>
}