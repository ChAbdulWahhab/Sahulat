package com.publicservices.pakistan.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionHelper(private val activity: Activity) {
    
    private var callPermissionLauncher: ActivityResultLauncher<String>? = null
    private var smsPermissionLauncher: ActivityResultLauncher<String>? = null
    
    fun registerCallPermissionLauncher(launcher: ActivityResultLauncher<String>) {
        callPermissionLauncher = launcher
    }
    
    fun registerSmsPermissionLauncher(launcher: ActivityResultLauncher<String>) {
        smsPermissionLauncher = launcher
    }
    
    fun hasCallPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    fun hasSmsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    fun requestCallPermission() {
        callPermissionLauncher?.launch(Manifest.permission.CALL_PHONE)
    }
    
    fun requestSmsPermission() {
        smsPermissionLauncher?.launch(Manifest.permission.SEND_SMS)
    }
}
