package com.example.musicapp.ui.theme.home

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.utils.PermissionsUtils

class MainActivity : AppCompatActivity() {

    private lateinit var vm:MainViewModel
    
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


}