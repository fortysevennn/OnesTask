package com.tugaypamuk.app.onestask.utils.enums

import java.util.Arrays

enum class VerifyStatusCode(val value : Int) {
    Success(0),
    NotFound(1),
    NotEnrolled(2),
    NotAllowedBioType(3),
    NotVerified(4),
    CardNotSupported(5);

    companion object{
        fun parse(value: Int) : VerifyStatusCode{
            return Arrays.stream(VerifyStatusCode.values()).filter{
                c : VerifyStatusCode ->
                c.value == value
            }.findFirst().orElse(NotFound)
        }
    }

}