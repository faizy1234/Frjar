package com.example.frjarcustomer.data.remote.repository

import com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.dto.response.user.UserResponse
import com.example.frjarcustomer.data.remote.endpoints.Endpoints
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface Repository {

    suspend fun getCustomerAppSetting(): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData>>>

    suspend fun getAllCustomerWalkThrough(): Flow<ApiResult<OnboardingData>>

    suspend fun getAuthToken(): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO>>>

    suspend fun checkAppVersion(): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto>>>

    suspend fun sendLoginOtp(phoneNumber: String?): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.user.UserResponse>>>
}
