package com.example.frjarcustomer.data.remote.repository

import com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.dto.response.otp.OtpResendResponse
import com.example.frjarcustomer.data.remote.dto.response.user.UserResponse
import com.example.frjarcustomer.data.remote.endpoints.Endpoints
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface Repository {

    suspend fun getCustomerAppSetting(): Flow<ApiResult<BaseResponse<AppSettingData>>>

    suspend fun getAllCustomerWalkThrough(): Flow<ApiResult<OnboardingData>>

    suspend fun getAuthToken(): Flow<ApiResult<BaseResponse<GetTokenDTO>>>

    suspend fun checkAppVersion(): Flow<ApiResult<BaseResponse<CheckAppVersionDto>>>

    suspend fun sendLoginOtp(phoneNumber: String?): Flow<ApiResult<BaseResponse<UserResponse>>>
    suspend fun loginWithPhone(
        phoneNumber: String? = null,
        password: String? = null,
        otp: String? = null,
        isPasswordSignIn: Boolean = false
    ): Flow<ApiResult<BaseResponse<UserResponse>>>


    suspend fun userVerification(
        otp: String? = null,
        isVerify: Boolean = false
    ): Flow<ApiResult<BaseResponse<UserResponse>>>

    suspend fun userResendOtp(): Flow<ApiResult<BaseResponse<OtpResendResponse>>>

    suspend fun registerWithPhone(phoneNumber: String?): Flow<ApiResult<BaseResponse<UserResponse>>>

    suspend fun userRegister(
        email: String, phoneNumber: String, password: String
    ): Flow<ApiResult<BaseResponse<UserResponse>>>


}
