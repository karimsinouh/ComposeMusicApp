package com.example.musicapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionsUtils {

    const val PERMISSIONS_REQUEST_CODE=1

    fun hasPermission(c:Context) =ContextCompat.checkSelfPermission(c,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED

}