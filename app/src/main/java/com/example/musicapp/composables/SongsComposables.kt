package com.example.musicapp.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.data.Song
import com.example.musicapp.ui.theme.colorBlack
import com.example.musicapp.ui.theme.colorPrimary

@Composable
fun SongItem(
    song: Song,
    playingSong: MutableState<Long?>,
    onPlay:()->Unit,
    onPause:()->Unit
){

    val isPlaying=playingSong.value.toString() ==song.id.toString()

    Log.d("wtf","current: ${playingSong.value}, this: ${song.id} ")

    val color = if (isPlaying) colorPrimary else colorBlack

    Row(
        Modifier.padding(8.dp),horizontalArrangement = Arrangement.Center) {
        Column{
            Text(text = song.title!!,fontWeight = FontWeight.Bold,fontSize = 18.sp,maxLines = 1,color = color)
            Text(text = song.artist!!,fontStyle = FontStyle.Italic,fontSize = 12.sp,maxLines = 1,color = color)
        }


        if(isPlaying)
            IconButton(onClick = { onPause() }) {
                Icon(imageVector = Icons.Outlined.Pause, contentDescription ="" )
            }
        else
            IconButton(onClick = { onPlay() }) {
            Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
            }

    }
}