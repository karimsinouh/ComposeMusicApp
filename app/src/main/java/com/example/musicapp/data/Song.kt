package com.example.musicapp.data

import android.content.ContentResolver
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import java.io.Serializable

data class Song(
    val title:String?="",
    val artist:String?="<Unknown>",
    val duration:Long?=0L,
    val id:Long?=0,
    val albumArt:String?="",
    val path:String?="",
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
       dest?.writeString(title)
       dest?.writeString(artist)
       dest?.writeValue(duration)
       dest?.writeValue(id)
       dest?.writeString(albumArt)
       dest?.writeString(path)
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }

}