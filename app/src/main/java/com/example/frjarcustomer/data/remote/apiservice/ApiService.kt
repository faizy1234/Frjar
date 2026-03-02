package com.example.frjarcustomer.data.remote.apiservice

import com.example.frjarcustomer.data.remote.dto.appsetting.AppSettingData
import com.example.frjarcustomer.data.remote.dto.appversion.CheckAppVersionDto
import com.example.frjarcustomer.data.remote.dto.baseResponse.BaseResponse
import com.example.frjarcustomer.data.remote.dto.getToken.GetTokenDTO
import com.example.frjarcustomer.data.remote.dto.onboarding.WalkThroughItemDto
import com.example.frjarcustomer.data.remote.endpoints.Endpoints
import com.example.frjarcustomer.data.remote.model.request.GenericBaseRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST(Endpoints.GET_CUSTOMER_APP_SETTINGS)
    suspend fun getCustomerAppSetting(
        @Body request: GenericBaseRequest
    ): BaseResponse<AppSettingData>


    @GET(Endpoints.GET_ALL_CUSTOMER_WALK_THROUGH)
    suspend fun getAllCustomerWalkThrough(): BaseResponse< List<WalkThroughItemDto>>


    @POST(Endpoints.GET_AUTH_TOKEN)
    suspend fun getAuthToken(
        @Body request: GenericBaseRequest
    ): BaseResponse<GetTokenDTO>


    @POST(Endpoints.GET_APP_VERSION)
    suspend fun checkAppVersion(
        @Body request: GenericBaseRequest
    ): BaseResponse<CheckAppVersionDto>



}