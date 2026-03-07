package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class DefaultAddressDto(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("city_tag") val cityTag: String? = null,
    @SerializedName("city_value") val cityValue: String? = null,
    @SerializedName("postal_code") val postalCode: String? = null,
    @SerializedName("is_selected") val isSelected: Boolean? = null,
    @SerializedName("district") val district: String? = null,
    @SerializedName("street_name") val streetName: String? = null,
    @SerializedName("building_no") val buildingNo: String? = null,
    @SerializedName("tag") val tag: String? = null,
    @SerializedName("short_address") val shortAddress: String? = null
)
