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


fun openMapLocation(
    context: Context?,
    latitude: Double?,
    longitude: Double?
) {

    if (context == null || latitude == null || longitude == null) return

    try {

        val uri = "geo:$latitude,$longitude?q=$latitude,$longitude".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val fallbackUri =
                "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude".toUri()
            val fallbackIntent = Intent(Intent.ACTION_VIEW, fallbackUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(fallbackIntent)
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}