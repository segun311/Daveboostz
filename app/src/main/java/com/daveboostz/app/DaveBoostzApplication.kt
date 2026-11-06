package com.daveboostz.app

import android.app.Application
import com.daveboostz.app.shizuku.ShizukuManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DaveBoostzApplication : Application() {
    lateinit var shizukuManager: ShizukuManager
    override fun onCreate() {
        super.onCreate()
        shizukuManager = ShizukuManager(this)
        shizukuManager.registerListeners()
    }
}
