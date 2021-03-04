package com.example.musicapp.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.example.musicapp.data.Result
import com.example.musicapp.data.Song

object Songs {

    fun get(contentResolver: ContentResolver, result: (Result<MutableList<Song>>) -> Unit) {
        val songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val albumArtUri = Uri.parse("content://media/external/audio/albumart")

        val cursor = contentResolver.query(songsUri, null, null, null, null, null)

        cursor?.let {

            if (cursor.moveToFirst()) {

                val list = mutableListOf<Song>()

                val nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val albumIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)

                do {

                    val id = cursor.getLong(idColumn)
                    val albumId = cursor.getLong(albumIdColumn)
                    val title = cursor.getString(nameColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getLong(durationColumn)

                    val audioFile = Uri.withAppendedPath(songsUri, id.toString())
                    val albumArtFile = Uri.withAppendedPath(albumArtUri, albumId.toString())

                    val song = Song(
                        title,
                        artist,
                        duration,
                        id,
                        albumArtFile.toString(),
                        audioFile.toString()
                    )

                    list.add(song)

                } while (cursor.moveToNext())

                cursor.close()

                if (list.isEmpty())
                    result(Result(false, null, "No Songs Found on this device"))
                else
                    result(Result(true, list))


            } else {
                result(Result(false, null, "No Songs Found on this device"))
            }

        }

    }

}