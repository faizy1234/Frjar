package com.example.frjarcustomer.data.remote.repository

import com.example.frjarcustomer.data.remote.dto.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCustomerAppSetting(): Flow<ApiResult<BaseResponse<AppSettingData>>>

    suspend fun getAllCustomerWalkThrough(): Flow<ApiResult<OnboardingData>>

    suspend fun getAuthToken(): Flow<ApiResult<BaseResponse<GetTokenDTO>>>

    suspend fun checkAppVersion(): Flow<ApiResult<BaseResponse<CheckAppVersionDto>>>
}
