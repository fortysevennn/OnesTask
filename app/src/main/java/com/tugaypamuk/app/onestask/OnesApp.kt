package com.tugaypamuk.app.onestask

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OnesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // karanlik tema kapatildi
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}