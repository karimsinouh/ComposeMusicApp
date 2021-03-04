package com.example.musicapp.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import com.example.musicapp.R
import com.example.musicapp.services.PlayerService

object ServiceUtils {

    fun getNotification(c: Context,isPlaying:Boolean,bitmap:Bitmap):NotificationCompat.Builder{

        val builder= NotificationCompat.Builder(c, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setLargeIcon(bitmap)
            .addAction(R.drawable.ic_previous,"Previous", pendingIntent(c, PREVIOUS_ACTION))

        if (isPlaying)
            builder.addAction(R.drawable.ic_pause,"Pause", pendingIntent(c, PAUSE_ACTION))
        else
            builder.addAction(R.drawable.ic_play,"Play", pendingIntent(c, RESUME_ACTION))

        builder.addAction(R.drawable.ic_next,"Next", pendingIntent(c, NEXT_ACTION))

        builder.setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1,2))

        return builder

    }



    private fun pendingIntent(c: Context, _action:String): PendingIntent {
        val i = Intent(c, PlayerService::class.java)
        i.action = _action
        return PendingIntent.getService(c, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}