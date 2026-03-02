package com.example.frjarcustomer.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>
) : NavType<T?>(isNullableAllowed = true){

    companion object{
        const val NULL = "null"
    }

    override fun get(bundle: Bundle, key: String): T? {
        return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            bundle.getParcelable(key,clazz)
        }else{
            bundle.getParcelable(key)
        }

    }

    override fun parseValue(value: String): T? {
        return if (value == NULL) null else Json.decodeFromString(serializer, Uri.decode(value))
    }
    override fun serializeAsValue(value: T?): String {
        return value?.let { Uri.encode(Json.encodeToString(serializer, it)) } ?: NULL
    }
    override fun put(bundle: Bundle, key: String, value: T?) {
        bundle.putParcelable(key,value)
    }




}