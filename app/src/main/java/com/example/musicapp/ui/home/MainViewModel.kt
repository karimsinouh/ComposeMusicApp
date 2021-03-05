package com.example.musicapp.ui.home

import android.content.ContentResolver
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.Song
import com.example.musicapp.utils.Songs

class MainViewModel:ViewModel() {

    val songs= mutableStateOf(mutableListOf<Song>())
    val error= mutableStateOf<String?>(null)
    val currentSongId= mutableStateOf<Long?>(null)
    val isPlaying= mutableStateOf(false)

    fun loadSongs(contentResolver: ContentResolver){
        Songs.get(contentResolver){
            if (it.isSuccessful)
                songs.value=it.data!!
            else
                setError(it.message)

        }
    }

    fun setError(it:String?){
        error.value=it
    }

    fun setCurrentSongId(id:Long){
        currentSongId.value = id
    }

    fun getItemById(id:Long):Song{
        var song:Song?=null
        for (s in songs.value){
            if (s.id==id) song=s
        }
        return song!!
    }

    fun getItemPosition(id:Long):Int{
        var position=0
        for (i in 0 until songs.value.size){
            if (id==songs.value[i].id)
                position=i
        }
        return position
    }

    fun setPlayingState(playing: Boolean) {
        isPlaying.value=playing
    }

}