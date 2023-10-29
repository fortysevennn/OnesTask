package com.tugaypamuk.app.onestask.utils

import java.net.Socket

fun StmpTest(host : String, port : Int) : String {
    return try {
        val socket = Socket(host,port)
        "SMTP Sunucusuna Başarılı Bir Şekilde Bağlandı"
    }catch (e : Exception){
        "SMTP Sunucusuna Bağlanırken Hata Oluştu: ${e.message}"
    }
}