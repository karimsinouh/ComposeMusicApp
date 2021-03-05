package com.example.musicapp.ui.home

import android.Manifest
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.R
import com.example.musicapp.composables.SongItem
import com.example.musicapp.data.Song
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.utils.*
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Player.EventListener,
    PlayerNotificationManager.MediaDescriptionAdapter {

    private lateinit var vm: MainViewModel
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private lateinit var player:SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicAppTheme {

                Scaffold {
                    Body(list = vm.songs.value)
                }

            }
        }

        vm=ViewModelProvider(this).get(MainViewModel::class.java)

        if (PermissionsUtils.hasPermission(this))
            vm.loadSongs(contentResolver)
        else {
            vm.setError("Your permission is required")
            askForPermission()
        }

        player=SimpleExoPlayer.Builder(this).build()

        player.addListener(this)

        playerNotificationManager= PlayerNotificationManager(this, NOTIFICATION_CHANNEL_ID, 1, this)


    }

    @Composable
    private fun Body(list: MutableList<Song>) {

        LazyColumn{
            items(list){
                SongItem(it,vm.currentSongId,{player.play()},{player.pause()})
            }
        }

        player.clearMediaItems()

        val mediaItems=list.map {
            MediaItem.Builder()
                .setMediaMetadata(MediaMetadata.Builder().setTitle(it.title).build())
                .setMediaId(it.id.toString())
                .setUri(Uri.parse(it.path))
                .build()
        }

        player.setMediaItems(mediaItems)
        player.prepare()
        playerNotificationManager.setPlayer(player)

    }

    private fun askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PermissionsUtils.PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun stop(){
        vm.currentSongId.value=null
        playerNotificationManager.setPlayer(null)
        player.stop()
        player.release()
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        vm.setError(error.message)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        vm.setCurrentSongId(mediaItem?.mediaId?.toLong()!!)
    }

    override fun getCurrentContentTitle(player: Player): CharSequence {
        val id=vm.currentSongId.value!!
        return vm.getItemById(id).title!!
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return PendingIntent.getActivity(this@MainActivity,
            1,
            Intent(this@MainActivity,MainActivity::class.java),
            FLAG_UPDATE_CURRENT)
    }

    override fun getCurrentContentText(player: Player): CharSequence {
        return "Something is playing now"
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        return BitmapFactory.decodeResource(resources, R.drawable.disc)
    }

}
