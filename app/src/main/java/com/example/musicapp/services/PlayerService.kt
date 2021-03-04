package com.example.musicapp.services

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LifecycleService
import com.example.musicapp.R
import com.example.musicapp.data.Song
import com.example.musicapp.utils.*
import com.example.musicapp.utils.ServiceUtils.getNotification
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerService:LifecycleService() {

    @Inject lateinit var player:SimpleExoPlayer

    private val songs= mutableListOf<Song>()
    private var firstPlay=true

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val temp=firstPlay

        when(intent?.action){

            START_ACTION->{
                if (firstPlay){
                    firstPlay=false
                    songs.addAll(intent.getParcelableArrayListExtra<Song>(PLAYLIST_EXTRA) as ArrayList<Song>)
                    start()
                }else{
                    player.play()
                }
            }

            RESUME_ACTION->resume()

            PAUSE_ACTION->pause()

            NEXT_ACTION->next()

            PREVIOUS_ACTION->previous()

            STOP_ACTION->stop()

        }

        val bitmap=BitmapFactory.decodeResource(resources, R.drawable.disc)
        val notification= getNotification(this,if(temp) true else player.isPlaying,bitmap)

        val position=findCurrentPosition()

        notification.setContentTitle(songs[position].title)
        notification.setSubText(songs[position].artist)

        startForeground(1,notification.build())

        return super.onStartCommand(intent, flags, startId)
    }

    private fun findCurrentPosition():Int{
        var position:Int?=0
        for (i in 0 until songs.size){
            if (songs[i].id.toString() == player.currentMediaItem?.mediaId!!)
                position=i
        }
        return position!!
    }

    private fun resume()=player.play()
    private fun pause()=player.pause()
    private fun next()=player.next()
    private fun previous()=player.previous()

    private fun start(){
        val mediaItems=songs.map {
            MediaItem.Builder().setUri(Uri.parse(it.path)).setMediaId(it.id.toString()).build()
        }
        player.setMediaItems(mediaItems)
        player.prepare()
        player.playWhenReady=true
    }

    private fun stop() {
        player.stop()
        player.release()
        stopSelf()
    }
}
