package com.example.frjarcustomer.data.remote.apiservice

import com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.dto.response.onboarding.WalkThroughItemDto
import com.example.frjarcustomer.data.remote.dto.response.user.UserResponse
import com.example.frjarcustomer.data.remote.endpoints.Endpoints
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST(Endpoints.GET_CUSTOMER_APP_SETTINGS)
    suspend fun getCustomerAppSetting(
        @Body request: GenericBaseRequest
    ): com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData>


    @GET(Endpoints.GET_ALL_CUSTOMER_WALK_THROUGH)
    suspend fun getAllCustomerWalkThrough(): com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<List<com.example.frjarcustomer.data.remote.dto.response.onboarding.WalkThroughItemDto>>


    @POST(Endpoints.GET_AUTH_TOKEN)
    suspend fun getAuthToken(
        @Body request: GenericBaseRequest
    ): com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO>


    @POST(Endpoints.GET_APP_VERSION)
    suspend fun checkAppVersion(
        @Body request: GenericBaseRequest
    ): com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto>


    @POST(Endpoints.SEND_LOGIN_OTP)
    suspend fun sendLoginOtp(@Body request: GenericBaseRequest): com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.user.UserResponse>


}