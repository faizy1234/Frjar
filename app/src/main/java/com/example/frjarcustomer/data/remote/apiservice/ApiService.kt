package com.example.frjarcustomer.data.remote.apiservice

import com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.dto.response.onboarding.WalkThroughItemDto
import com.example.frjarcustomer.data.remote.dto.response.otp.OtpResendResponse
import com.example.frjarcustomer.data.remote.dto.response.user.UserResponse
import com.example.frjarcustomer.data.remote.endpoints.Endpoints
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import com.example.frjarcustomer.data.remote.model.request.UserRequest
import com.google.android.gms.common.api.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST(Endpoints.GET_CUSTOMER_APP_SETTINGS)
    suspend fun getCustomerAppSetting(
        @Body request: GenericBaseRequest
    ): BaseResponse<AppSettingData>


    @GET(Endpoints.GET_ALL_CUSTOMER_WALK_THROUGH)
    suspend fun getAllCustomerWalkThrough(): BaseResponse<List<WalkThroughItemDto>>


    @POST(Endpoints.GET_AUTH_TOKEN)
    suspend fun getAuthToken(
        @Body request: GenericBaseRequest
    ): BaseResponse<GetTokenDTO>


    @POST(Endpoints.GET_APP_VERSION)
    suspend fun checkAppVersion(
        @Body request: GenericBaseRequest
    ): BaseResponse<CheckAppVersionDto>

    ////LOgin flow aPis

    @POST(Endpoints.SEND_LOGIN_OTP)
    suspend fun sendLoginOtp(@Body request: GenericBaseRequest): BaseResponse<UserResponse>

    @POST(Endpoints.LOGIN_WITH_PHONE)
    suspend fun loginWithPhone(@Body userRequest: GenericBaseRequest): BaseResponse<UserResponse>

    @POST(Endpoints.USER_RESEND_OTP)
    suspend fun userResendOtp(@Body request: GenericBaseRequest): BaseResponse<OtpResendResponse>



    @POST(Endpoints.REGISTER_WITH_PHONE)
    suspend fun registerWithPhone(@Body request: GenericBaseRequest): BaseResponse<UserResponse>

}