package com.example.musicapp.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.data.Song
import com.example.musicapp.ui.theme.colorBlack
import com.example.musicapp.ui.theme.colorPrimary
import com.example.musicapp.ui.theme.colorWhite

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

        Column(
            Modifier
                .weight(3f)
                .fillMaxWidth()){
            Text(text = song.title!!,fontWeight = FontWeight.Bold,fontSize = 18.sp,maxLines = 1,color = color)
            Text(text = song.artist!!,fontStyle = FontStyle.Italic,fontSize = 12.sp,maxLines = 1,color = color)
        }


        if(isCurrentSong){
                if (isCurrentlyPlaying){
                    //show pause button
                    IconButton(onClick = { onPause() },
                        Modifier
                            .weight(0.5f)
                            .wrapContentWidth()) {
                        Icon(imageVector = Icons.Outlined.Pause, contentDescription ="" )
                    }

                }else{
                    //show play button
                    IconButton(onClick = { onPlay() },
                        Modifier
                            .weight(0.5f)
                            .wrapContentWidth()) {
                        Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
                    }
                }
        } else
            //show play button
            IconButton(onClick = { onPlay() },
                Modifier
                    .weight(0.5f)
                    .wrapContentWidth()) {
                Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
            }

    }
}


@Composable
@Preview
fun SongItemPreview(
){
    Row(Modifier.padding(8.dp)) {

        Column(modifier = Modifier
            .weight(3f)
            .fillMaxWidth()){
            Text(text = "song.title!!song.title!!song.title!!song.title!!song.title!!",fontWeight = FontWeight.Bold,fontSize = 18.sp,maxLines = 1,)
            Text(text = "song.artist!!",fontStyle = FontStyle.Italic,fontSize = 12.sp,maxLines = 1)
        }



            IconButton(onClick = {  },modifier = Modifier
                .weight(0.5f)
                .wrapContentWidth()) {
                Icon(imageVector = Icons.Outlined.Pause, contentDescription ="" )
            }

            IconButton(onClick = {  },modifier = Modifier
                .weight(0.5f)
                .wrapContentWidth()) {
                Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription ="" )
            }

    }
}

@Composable
fun Drawer(@DrawableRes headerImage:Int){
    Column {

        //header image
        Image(painter = painterResource(id = headerImage),
            contentDescription = "",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Row(Modifier.padding(12.dp),horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription ="" )
            Text(text = "Liked songs")
        }
        Divider()
        Row(Modifier.padding(12.dp),horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Headphones, contentDescription ="" )
            Text(text = "Listen")
        }
        Divider()
        Row(Modifier.padding(12.dp),horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Share, contentDescription ="" )
            Text(text = "Share")
        }
        Divider()
    }
}

@Composable
@Preview
fun MainAppBar(){
    TopAppBar(backgroundColor = colorWhite,elevation = 0.dp,content = {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Outlined.Audiotrack,
                contentDescription = "")
            Text(
                text = "Compose Player",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }

    })
}