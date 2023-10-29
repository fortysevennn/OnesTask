package com.tugaypamuk.app.onestask.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tugaypamuk.app.onestask.domain.model.AccessLog

/*
 UserID NVARCHAR(256) (NOT NULL) Kullanıcı ID
Username NVARCHAR(64) (NOT NULL) Kullanıcı Ad Soyad
AccessLocation NVARCHAR(MAX) (NOT NULL) Erişim Yapılan Yer
AccessDirection INT (NOT NULL) Erişim Yönü (Enum - Aşağıda detayı verildi)
VerifyStatusCode INT (NOT NULL) Log Durum Kodu (Enum - Aşağıda detayı verildi)
AdditionalInfo NVARCHAR(4000) (NOT NULL) İlave Bilgi
LogTime DATETIME (NOT NULL) Log Zamanı
LogID BIGINT (NOT NULL) (IDENTITY 1,1) Log ID
ComputerHash NVARCHAR(32) (NOT NULL) Log'un oluştuğu cihaz ID
IPAddress NVARCHAR(64) (NOT NULL) Log'un oluştuğu cihaz IP
  */
@Entity(tableName = "ones_logs")
data class OnesLogEntitiy(
    val accessDirection: Int?,
    val accessLocation: String?,
    val additionalInfo: String?,
    val computerHash: String?,
    val ipAddress: String?,
    @PrimaryKey
    val logID: String,
    val logTime: String?,
    val userID: String?,
    val username: String?,
    val verifyStatusCode: Int?
)

