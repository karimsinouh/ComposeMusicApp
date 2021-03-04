package com.example.musicapp.ui.home

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.services.PlayerService
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val list=vm.songs.value

                    LazyColumn{
                        items(list){song->
                            Column(Modifier.padding(8.dp)) {
                                Text(song.title!!,fontSize = 18.sp,fontWeight = FontWeight.Bold)
                                Text(song.artist!!)
                                Button(onClick = { sendCommand(START_ACTION)}) {
                                    Text(text = "start")
                                }

                                Button(onClick = { sendCommand(STOP_ACTION)}) {
                                    Text(text = "stop")
                                }
                            }
                        }
                    }

                    vm.error.value?.let {
                        if(list.isNotEmpty())
                            Text(text = it)
                    }

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

    }


    private fun askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PermissionsUtils.PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun sendCommand(command:String){
        val i=Intent(this,PlayerService::class.java)
        i.action=command
        i.putExtra(PLAYLIST_EXTRA,ArrayList(vm.songs.value))
        startService(i)
    }


}
