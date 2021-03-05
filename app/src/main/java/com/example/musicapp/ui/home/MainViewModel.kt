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

}