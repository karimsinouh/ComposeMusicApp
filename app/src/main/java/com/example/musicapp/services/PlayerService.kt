package com.example.musicapp.services

import android.content.Intent
import androidx.lifecycle.LifecycleService

class PlayerService:LifecycleService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

}