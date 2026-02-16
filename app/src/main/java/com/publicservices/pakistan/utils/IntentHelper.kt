package com.publicservices.pakistan.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentHelper {
    
    fun sendSms(context: Context, number: String, body: String = "") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:$number")
                if (body.isNotEmpty()) {
                    putExtra("sms_body", body)
                }
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open SMS app", Toast.LENGTH_SHORT).show()
        }
    }
    
    fun makeCall(context: Context, number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open dialer", Toast.LENGTH_SHORT).show()
        }
    }
    
    fun copyToClipboard(context: Context, text: String, label: String = "Service Number") {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Copied: $text", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to copy", Toast.LENGTH_SHORT).show()
        }
    }
}
