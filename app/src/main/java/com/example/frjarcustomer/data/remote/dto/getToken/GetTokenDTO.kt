package com.example.frjarcustomer.data.remote.dto.getToken

import com.google.gson.annotations.SerializedName

data class GetTokenDTO(

    @SerializedName("token")
    val token: String? = null

)