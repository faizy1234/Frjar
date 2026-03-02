package com.example.frjarcustomer.data.remote.repository

import com.example.frjarcustomer.core.config.AppConfig
import com.example.frjarcustomer.core.di.CoilModule
import com.example.frjarcustomer.core.di.ImageCacheWarmer
import com.example.frjarcustomer.core.di.IoDispatcher
import com.example.frjarcustomer.core.di.LanguageProvider
import com.example.frjarcustomer.core.di.StringProvider
import com.example.frjarcustomer.core.fcm.FcmRepository
import com.example.frjarcustomer.core.session.SessionManager
import com.example.frjarcustomer.data.remote.apiservice.ApiService
import com.example.frjarcustomer.data.remote.dto.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.toOnboardingData
import com.example.frjarcustomer.data.remote.utils.ApiResult
import com.example.frjarcustomer.data.remote.utils.safeApiCall
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepositoryImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService,
    private val stringProvider: StringProvider,
    private val fcmRepository: FcmRepository,
    private val languageProvider: LanguageProvider,
    private val appConfig: AppConfig,
    private val sessionManager: SessionManager,
    private  val imageWarmer: ImageCacheWarmer
) : Repository {

    private suspend fun createBaseRequest() = GenericBaseRequest(
        deviceId = fcmRepository.getDeviceId(),
        appLang = languageProvider.getLanguageCode(),
        appVersion = appConfig.versionName.toDoubleOrNull()
    )

    override suspend fun getCustomerAppSetting(): Flow<ApiResult<BaseResponse<AppSettingData>>> =
        safeApiCall(
            ioDispatcher,
            { apiService.getCustomerAppSetting(createBaseRequest()) },
            stringProvider
        )

    override suspend fun getAllCustomerWalkThrough(): Flow<ApiResult<OnboardingData>> =
        safeApiCall(ioDispatcher, { apiService.getAllCustomerWalkThrough() }, stringProvider)
            .map { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val onboardingData = result.data.data?.toOnboardingData()
                            ?: OnboardingData(emptyList())

                        imageWarmer.preloadUrls(onboardingData.pages?.mapNotNull { it.imageRes } ?: emptyList())
                        ApiResult.Success(onboardingData)
                    }

                    is ApiResult.Error -> result
                    is ApiResult.Loading -> result
                }
            }

    override suspend fun getAuthToken(): Flow<ApiResult<BaseResponse<GetTokenDTO>>> = flow {
        safeApiCall(
            ioDispatcher,
            { apiService.getAuthToken(createBaseRequest()) },
            stringProvider
        ).collect { result ->
            if (result is ApiResult.Success) result.data.data?.token?.let {
                sessionManager.setToken(
                    it
                )
            }
            emit(result)
        }
    }

    override suspend fun checkAppVersion(): Flow<ApiResult<BaseResponse<CheckAppVersionDto>>> =
        safeApiCall(
            ioDispatcher,
            { apiService.checkAppVersion(createBaseRequest()) },
            stringProvider
        )


}