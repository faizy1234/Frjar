package com.example.frjarcustomer.data.remote.model.appSetting

import com.google.gson.annotations.SerializedName

data class EssentialAppSetting(
    val contactNo: String? = null,
    val maintenanceMessageLocalized: String? = null,
    val appUpdateMessageLocalized: String? = null,
    val androidCustomerUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,


    )
