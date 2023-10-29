package com.tugaypamuk.app.onestask.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ones_mail")
data class MailSettingsEntity(
    @PrimaryKey
    val id : Int?,
    val host : String?,
    val port : String?,
    val username : String?,
    val password : String?,
    val sendFrom : String?

)