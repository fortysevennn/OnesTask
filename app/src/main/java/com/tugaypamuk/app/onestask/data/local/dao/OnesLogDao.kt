package com.tugaypamuk.app.onestask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import kotlinx.coroutines.flow.Flow

@Dao
interface OnesLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLogs(logs : OnesLogEntitiy?)

    @Query("SELECT * FROM ones_logs ORDER BY logTime")
    fun getAllLogs() : Flow<List<OnesLogEntitiy>>
}