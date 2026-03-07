package com.example.frjarcustomer.data.remote.dto.response.appversion

import com.example.frjarcustomer.data.remote.utils.AppUpdateStatus
import com.google.gson.annotations.SerializedName

data class CheckAppVersionDto(

    @SerializedName("version_update_type")
    val versionUpdateType: String? = null

) {

    val isUpToDated: Boolean
        get() = versionUpdateType?.trim() == AppUpdateStatus.UpToDate.value
    val isRecommendedUpdate: Boolean
        get() = versionUpdateType?.trim() == AppUpdateStatus.RecommendedUpdate.value

    val isForceUpdate: Boolean
        get() = versionUpdateType?.trim() == AppUpdateStatus.ForceUpdate.value

}