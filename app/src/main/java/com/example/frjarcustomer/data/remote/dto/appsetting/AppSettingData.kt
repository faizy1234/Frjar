package com.example.frjarcustomer.data.remote.dto.appsetting

import com.example.frjarcustomer.data.remote.utils.AppLanguage
import com.example.frjarcustomer.utils.LocaleManager
import com.google.gson.annotations.SerializedName

data class AppSettingData(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("is_force_update") val isForceUpdate: Boolean? = null,
    @SerializedName("ios_app_version") val iosAppVersion: String? = null,
    @SerializedName("android_app_version") val androidAppVersion: String? = null,
    @SerializedName("android_maintainace_mode") val androidMaintainaceMode: Boolean? = null,
    @SerializedName("ios_maintainace_mode") val iosMaintainaceMode: Boolean? = null,
    @SerializedName("app_region") val appRegion: String? = null,
    @SerializedName("status") val status: Boolean? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("__v") val v: Int? = null,
    @SerializedName("app_type") val appType: String? = null,
    @SerializedName("web_maintainace_mode") val webMaintainaceMode: Boolean? = null,
    @SerializedName("vat_percentage") val vatPercentage: String? = null,
    @SerializedName("api_url") val apiUrl: String? = null,
    @SerializedName("is_location_required") val isLocationRequired: Boolean? = null,
    @SerializedName("app_logo") val appLogo: String? = null,
    @SerializedName("dark_app_logo") val darkAppLogo: String? = null,
    @SerializedName("otp_time_limit") val otpTimeLimit: String? = null,
    @SerializedName("alert_message_ar") val alertMessageAr: String? = null,
    @SerializedName("alert_message_en") val alertMessageEn: String? = null,
    @SerializedName("is_alert_message") val isAlertMessage: Boolean? = null,
    @SerializedName("alert_title_ar") val alertTitleAr: String? = null,
    @SerializedName("alert_title_en") val alertTitleEn: String? = null,
    @SerializedName("building_material_allow_order") val buildingMaterialAllowOrder: Boolean? = null,
    @SerializedName("equipment_rent_allow_order") val equipmentRentAllowOrder: Boolean? = null,
    @SerializedName("equipment_sale_allow_order") val equipmentSaleAllowOrder: Boolean? = null,
    @SerializedName("app_update_message_ar") val appUpdateMessageAr: String? = null,
    @SerializedName("app_update_message_en") val appUpdateMessageEn: String? = null,
    @SerializedName("app_update_message_hi") val appUpdateMessageHi: String? = null,
    @SerializedName("app_update_message_ur") val appUpdateMessageUr: String? = null,
    @SerializedName("ios_app_url") val iosAppUrl: String? = null,
    @SerializedName("android_provider_url") val androidProviderUrl: String? = null,
    @SerializedName("android_customer_url") val androidCustomerUrl: String? = null,
    @SerializedName("ios_provider_url") val iosProviderUrl: String? = null,
    @SerializedName("ios_customer_url") val iosCustomerUrl: String? = null,
    @SerializedName("app_maintenance_message_ar") val appMaintenanceMessageAr: String? = null,
    @SerializedName("app_maintenance_message_en") val appMaintenanceMessageEn: String? = null,
    @SerializedName("app_maintenance_message_hi") val appMaintenanceMessageHi: String? = null,
    @SerializedName("app_maintenance_message_ur") val appMaintenanceMessageUr: String? = null,
    @SerializedName("aws_secret_key") val awsSecretKey: String? = null,
    @SerializedName("aws_access_key") val awsAccessKey: String? = null,
    @SerializedName("android_app_url") val androidAppUrl: String? = null,
    @SerializedName("aws_bucket_name") val awsBucketName: String? = null,
    @SerializedName("aws_region") val awsRegion: String? = null,
    @SerializedName("en_about_us_url") val enAboutUsUrl: String? = null,
    @SerializedName("en_faqs_url") val enFaqsUrl: String? = null,
    @SerializedName("en_privacy_url") val enPrivacyUrl: String? = null,
    @SerializedName("en_terms_conditions_url") val enTermsConditionsUrl: String? = null,
    @SerializedName("ar_about_us_url") val arAboutUsUrl: String? = null,
    @SerializedName("ar_faqs_url") val arFaqsUrl: String? = null,
    @SerializedName("ar_privacy_url") val arPrivacyUrl: String? = null,
    @SerializedName("ar_terms_conditions_url") val arTermsConditionsUrl: String? = null,
    @SerializedName("ar_return_url") val arReturnUrl: String? = null,
    @SerializedName("en_return_url") val enReturnUrl: String? = null,
    @SerializedName("web_app_version") val webAppVersion: String? = null,
    @SerializedName("delete_reason_arr") val deleteReasonArr: List<DeleteReasonItem>? = null,
    @SerializedName("web_base_url") val webBaseUrl: String? = null,
    @SerializedName("customer_support_no") val customerSupportNo: String? = null,
    @SerializedName("sales_dept_no") val salesDeptNo: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("address_ar") val addressAr: String? = null,
    @SerializedName("comp_lat") val compLat: Double? = null,
    @SerializedName("comp_lng") val compLng: Double? = null,
    @SerializedName("customer_support_email") val customerSupportEmail: String? = null,
    @SerializedName("need_help_ar") val needHelpAr: String? = null,
    @SerializedName("need_help_en") val needHelpEn: String? = null,
    @SerializedName("provider_url") val providerUrl: String? = null,
    @SerializedName("customer_url") val customerUrl: String? = null,
    @SerializedName("provider_register_url") val providerRegisterUrl: String? = null,
    @SerializedName("home_banner_building_ar") val homeBannerBuildingAr: String? = null,
    @SerializedName("home_banner_building_en") val homeBannerBuildingEn: String? = null,
    @SerializedName("home_banner_sale_en") val homeBannerSaleEn: String? = null,
    @SerializedName("home_banner_sale_ar") val homeBannerSaleAr: String? = null,
    @SerializedName("home_banner_rent_ar") val homeBannerRentAr: String? = null,
    @SerializedName("home_banner_rent_en") val homeBannerRentEn: String? = null,
    @SerializedName("marketing_email") val marketingEmail: String? = null,
    @SerializedName("landing_mockup") val landingMockup: String? = null,
    @SerializedName("social_url_arr") val socialUrlArr: SocialUrlArr? = null,
    @SerializedName("is_otp") val isOtp: Boolean? = null,
    @SerializedName("hire_tech_web_img") val hireTechWebImg: String? = null,
    @SerializedName("project_banner") val projectBanner: ProjectBannerDto? = null,
    @SerializedName("project_unauth_text") val projectUnauthText: ProjectUnauthTextDto? = null,
    @SerializedName("project_subscription_arr") val projectSubscriptionArr: List<ProjectSubscriptionItem>? = null,
    @SerializedName("project_feature_arr") val projectFeatureArr: List<ProjectFeatureItem>? = null,
    @SerializedName("allow_placing_order_expired") val allowPlacingOrderExpired: String? = null,
    @SerializedName("version_update_type") val versionUpdateType: String? = null,
    @SerializedName("quotation_data") val quotationData: QuotationDataDto? = null,
    @SerializedName("is_quote_cust_display") val isQuoteCustDisplay: Boolean? = null,
    @SerializedName("is_force_logout") val isForceLogout: Boolean? = null,
    @SerializedName("is_sample_enable") val isSampleEnable: Boolean? = null,
    @SerializedName("policy_version") val policyVersion: Int? = null,
    @SerializedName("is_display_vat_amt") val isDisplayVatAmt: Boolean? = null,
    @SerializedName("payment_gateway") val paymentGateway: PaymentGatewayDto? = null,
    @SerializedName("is_new_currency") val isNewCurrency: Boolean? = null,
    @SerializedName("is_join_request") val isJoinRequest: Boolean? = null,
    @SerializedName("other_languages_disabled") val otherLanguagesDisabled: Boolean? = null,
    @SerializedName("ai_url") val aiUrl: String? = null,
    @SerializedName("is_log") val isLog: Boolean? = null,
    @SerializedName("is_no_address_allowed") val isNoAddressAllowed: Boolean? = null,
    @SerializedName("is_guest_allowed") val isGuestAllowed: Boolean? = null,
    @SerializedName("today_date") val todayDate: String? = null,
    @SerializedName("banner") val banner: List<BannerItem>? = null,
    @SerializedName("services") val services: List<ServiceItem>? = null,
    @SerializedName("categories") val categories: List<CategoryItem>? = null,
    @SerializedName("offer_arr") val offerArr: List<OfferItem>? = null,
    @SerializedName("feature_products") val featureProducts: List<FeatureProductItem>? = null,
    @SerializedName("video") val video: VideoDto? = null,
    @SerializedName("feature_companies") val featureCompanies: List<FeatureCompanyItem>? = null,
    @SerializedName("default_address") val defaultAddress: DefaultAddressDto? = null
){
    val maintenanceMessageLocalized: String
        get() = (when (LocaleManager.currentLanguage) {
            AppLanguage.ARABIC.value -> appMaintenanceMessageAr ?: appMaintenanceMessageEn
            AppLanguage.HINDI.value -> appMaintenanceMessageHi ?: appMaintenanceMessageEn
            AppLanguage.URDU.value -> appMaintenanceMessageUr ?: appMaintenanceMessageEn
            AppLanguage.ENGLISH.value -> appMaintenanceMessageEn
            null -> appMaintenanceMessageEn
            else -> appMaintenanceMessageEn
        } ?: "")

    val appUpdateMessageLocalized: String
        get() = (when (LocaleManager.currentLanguage) {
            AppLanguage.ARABIC.value -> appUpdateMessageAr ?: appUpdateMessageEn
            AppLanguage.HINDI.value -> appUpdateMessageHi ?: appUpdateMessageEn
            AppLanguage.URDU.value -> appUpdateMessageUr ?: appUpdateMessageEn
            AppLanguage.ENGLISH.value -> appUpdateMessageEn
            null -> appUpdateMessageEn
            else -> appUpdateMessageEn
        } ?: "")


}
