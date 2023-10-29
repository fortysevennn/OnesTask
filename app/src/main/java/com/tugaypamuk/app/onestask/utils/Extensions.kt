package com.tugaypamuk.app.onestask.utils

import android.widget.TextView
import com.tugaypamuk.app.onestask.R
import com.tugaypamuk.app.onestask.utils.enums.VerifyStatusCode



// Kotlin Extensions Textview icerisinde VerifyStatusCode a gore hata mesajlarinin donusumu yapilmakta
fun TextView.statusCode( type : Int){
     when(VerifyStatusCode.parse(type)){
        VerifyStatusCode.NotFound -> this.text = context.getString(R.string.user_not_found)
        VerifyStatusCode.NotEnrolled -> this.text =context.getString(R.string.user_not_enrolled)
        VerifyStatusCode.NotAllowedBioType -> this.text =context.getString(R.string.user_not_allowed_bio_type)
        VerifyStatusCode.NotVerified -> this.text =context.getString(R.string.user_not_verified)
        VerifyStatusCode.CardNotSupported -> this.text =context.getString(R.string.user_not_card_not_supported)
        else -> this.text =context.getString(R.string.user_not_supported_value)
    }

}

