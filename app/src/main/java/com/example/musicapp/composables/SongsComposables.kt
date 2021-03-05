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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.data.Song
import com.example.musicapp.ui.theme.colorBlack
import com.example.musicapp.ui.theme.colorPrimary

@Composable
fun SongItem(
    song: Song,
    playingSong: MutableState<Long?>,
    isCurrentlyPlaying:Boolean,
    onPlay:()->Unit,
    onPause:()->Unit
){

    val isCurrentSong=playingSong.value.toString() ==song.id.toString()

    val color = if (isCurrentSong) colorPrimary else colorBlack

    Row(
        Modifier.padding(8.dp),horizontalArrangement = Arrangement.Center) {

        Column(Modifier.weight(3f).fillMaxWidth()){
            Text(text = song.title!!,fontWeight = FontWeight.Bold,fontSize = 18.sp,maxLines = 1,color = color)
            Text(text = song.artist!!,fontStyle = FontStyle.Italic,fontSize = 12.sp,maxLines = 1,color = color)
        }


        if(isCurrentSong){
                if (isCurrentlyPlaying){
                    //show pause button
                    IconButton(onClick = { onPause() },Modifier.weight(0.5f).wrapContentWidth()) {
                        Icon(imageVector = Icons.Outlined.Pause, contentDescription ="" )
                    }

                }else{
                    //show play button
                    IconButton(onClick = { onPlay() },Modifier.weight(0.5f).wrapContentWidth()) {
                        Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
                    }
                }
        } else
            //show play button
            IconButton(onClick = { onPlay() },Modifier.weight(0.5f).wrapContentWidth()) {
                Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
            }

    }
}


@Composable
@Preview
fun SongItemPreview(
){


    Row(
        Modifier.padding(8.dp)) {

        Column(modifier = Modifier.weight(3f).fillMaxWidth()){
            Text(text = "song.title!!song.title!!song.title!!song.title!!song.title!!",fontWeight = FontWeight.Bold,fontSize = 18.sp,maxLines = 1,)
            Text(text = "song.artist!!",fontStyle = FontStyle.Italic,fontSize = 12.sp,maxLines = 1)
        }



            IconButton(onClick = {  },modifier = Modifier.weight(0.5f).wrapContentWidth()) {
                Icon(imageVector = Icons.Outlined.Pause, contentDescription ="" )
            }

            IconButton(onClick = {  },modifier = Modifier.weight(0.5f).wrapContentWidth()) {
                Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
            }

    }
}