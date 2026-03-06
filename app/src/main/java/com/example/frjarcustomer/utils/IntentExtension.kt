package com.example.frjarcustomer.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun openPlayStore(context: Context?, appUrl: String?) {

    if (context == null || appUrl.isNullOrBlank()) return

    try {
        val uri = appUrl.trim().toUri()

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = uri
            setPackage("com.android.vending")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

    } catch (e: Exception) {

        try {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                appUrl.trim().toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(browserIntent)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

fun openDialer(context: Context?, phoneNumber: String?) {

    if (context == null || phoneNumber.isNullOrBlank()) return

    try {
        val cleanedNumber = phoneNumber.trim()

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$cleanedNumber".toUri()
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}