package com.example.frjarcustomer.data.remote.endpoints


object Endpoints {
    private const val PREFIX = "api/"
    const val GET_ALL_CUSTOMER_WALK_THROUGH = "${PREFIX}customer/setting/getallcustomerWalkThroughs"
    const val GET_CUSTOMER_APP_SETTINGS = "${PREFIX}customer/setting/getCustomerAppSetting"
    const val GET_AUTH_TOKEN = "${PREFIX}customer/setting/getAuthToken"
    const val GET_APP_VERSION = "${PREFIX}customer/setting/checkAppVersion"
    const val SEND_LOGIN_OTP = "${PREFIX}user/sendLoginOtp"


}