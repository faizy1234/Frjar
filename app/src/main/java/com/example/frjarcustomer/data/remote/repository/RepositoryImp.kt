package com.example.frjarcustomer.data.remote.repository

import com.example.frjarcustomer.core.config.AppConfig
import com.example.frjarcustomer.core.di.ImageCacheWarmer
import com.example.frjarcustomer.core.di.IoDispatcher
import com.example.frjarcustomer.core.di.LanguageProvider
import com.example.frjarcustomer.core.di.StringProvider
import com.example.frjarcustomer.core.fcm.FcmRepository
import com.example.frjarcustomer.core.session.SessionManager
import com.example.frjarcustomer.data.remote.apiservice.ApiService
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.response.otp.OtpResendResponse
import com.example.frjarcustomer.data.remote.dto.response.user.UserResponse
import com.example.frjarcustomer.data.remote.endpoints.Endpoints.NUMBER_PREFIX
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import com.example.frjarcustomer.data.remote.model.request.UserRequest
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.toOnboardingData
import com.example.frjarcustomer.data.remote.utils.ApiResult
import com.example.frjarcustomer.data.remote.utils.ensureSuccessCode
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
    private val imageWarmer: ImageCacheWarmer
) : Repository {

    private suspend fun createBaseRequest(phoneNumber: String? = null) = GenericBaseRequest(
        userId = sessionManager.getUser()?.userId ?: fcmRepository.getDeviceId(),
        deviceId = fcmRepository.getDeviceId(),
        appLang = languageProvider.getLanguageCode(),
        appVersion = appConfig.versionName.toDoubleOrNull(),
        phone = phoneNumber
    )

    private suspend fun createUserRequest(
        phoneNumber: String? = null,
        password: String? = null,
        otp: String? = null,
        isPasswordSignIn: Boolean = false
    ) =
        GenericBaseRequest(
            userId = sessionManager.getUser()?.userId ?: fcmRepository.getDeviceId(),
            deviceId = fcmRepository.getDeviceId(),
            appLang = languageProvider.getLanguageCode(),
            appVersion = appConfig.versionName.toDoubleOrNull(),
            phone = if (phoneNumber != null) NUMBER_PREFIX + phoneNumber else null,
            password = if (isPasswordSignIn) password else null,
            firebaseToken = fcmRepository.getCurrentToken(),
            otp = if (!isPasswordSignIn) otp else null

        )


    override suspend fun getCustomerAppSetting(): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.appsetting.AppSettingData>>> =
        safeApiCall(
            ioDispatcher,
            { apiService.getCustomerAppSetting(createBaseRequest()) },
            stringProvider,
            languageProvider.getLanguageCode()
        ).ensureSuccessCode(languageProvider, stringProvider)

    override suspend fun getAllCustomerWalkThrough(): Flow<ApiResult<OnboardingData>> =
        safeApiCall(
            ioDispatcher,
            { apiService.getAllCustomerWalkThrough() },
            stringProvider,
            languageProvider.getLanguageCode()
        )
            .ensureSuccessCode(languageProvider, stringProvider)
            .map { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val onboardingData = result.data.data?.toOnboardingData()
                            ?: OnboardingData(emptyList())

                        imageWarmer.preloadUrls(onboardingData.pages?.mapNotNull { it.imageRes }
                            ?: emptyList())
                        ApiResult.Success(onboardingData)
                    }

                    is ApiResult.Error -> result
                    is ApiResult.Loading -> result
                }
            }

    override suspend fun getAuthToken(): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.getToken.GetTokenDTO>>> =
        flow {
            safeApiCall(
                ioDispatcher,
                { apiService.getAuthToken(createBaseRequest()) },
                stringProvider,
                languageProvider.getLanguageCode()
            ).ensureSuccessCode(languageProvider, stringProvider)
                .collect { result ->
                    if (result is ApiResult.Success) result.data.data?.token?.let {
                        sessionManager.setToken(
                            it
                        )
                    }
                    emit(result)
                }
        }

    override suspend fun checkAppVersion(): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.appversion.CheckAppVersionDto>>> =
        safeApiCall(
            ioDispatcher,
            { apiService.checkAppVersion(createBaseRequest()) },
            stringProvider,
            languageProvider.getLanguageCode()
        ).ensureSuccessCode(languageProvider, stringProvider)

    override suspend fun sendLoginOtp(phoneNumber: String?): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<com.example.frjarcustomer.data.remote.dto.response.user.UserResponse>>> =
        safeApiCall(
            ioDispatcher,
            { apiService.sendLoginOtp(createBaseRequest(phoneNumber)) },
            stringProvider,
            languageProvider.getLanguageCode()
        ).ensureSuccessCode(languageProvider, stringProvider)

    override suspend fun loginWithPhone(
        phoneNumber: String?,
        password: String?,
        otp: String?,
        isPasswordSignIn: Boolean
    ): Flow<ApiResult<BaseResponse<UserResponse>>> =
        flow {
            safeApiCall(
                ioDispatcher,
                {
                    apiService.loginWithPhone(
                        createUserRequest(
                            phoneNumber = phoneNumber,
                            password = password,
                            otp = otp,
                            isPasswordSignIn = isPasswordSignIn
                        )
                    )
                },
                stringProvider,
                languageProvider.getLanguageCode()
            ).ensureSuccessCode(languageProvider, stringProvider)
                .collect { result ->
                    if (result is ApiResult.Success) result.data.data?.let {
                        sessionManager.setToken(
                            it.token
                        )
                        sessionManager.setUser(it)
                    }
                    emit(result)
                }
        }


    override suspend fun userResendOtp(): Flow<ApiResult<BaseResponse<OtpResendResponse>>> =
        safeApiCall(
            ioDispatcher,
            { apiService.userResendOtp(createBaseRequest(null)) },
            stringProvider,
            languageProvider.getLanguageCode()
        ).ensureSuccessCode(languageProvider, stringProvider)

}



