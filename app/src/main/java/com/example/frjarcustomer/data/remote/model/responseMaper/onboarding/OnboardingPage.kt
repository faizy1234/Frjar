package com.example.frjarcustomer.data.remote.model.responseMaper.onboarding

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.frjarcustomer.data.remote.dto.onboarding.WalkThroughItemDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class OnboardingData(
    val pages: List<OnboardingPage>?
): Parcelable


@Serializable
@Parcelize
@Immutable
data class OnboardingPage(
    val title: String?,
    val description: String?,
    val imageRes: String?
): Parcelable


fun List<WalkThroughItemDto>.toOnboardingData(): OnboardingData =
    OnboardingData(
        pages = this
            ?.filter { it.status == true }
            ?.map { item ->
                OnboardingPage(
                    title = item.titleEn,
                    description = item.descEn,
                    imageRes = item.imageUrl
                )
            } ?: emptyList()
    )