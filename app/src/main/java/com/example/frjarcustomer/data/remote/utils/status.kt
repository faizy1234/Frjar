package com.example.frjarcustomer.data.remote.utils

enum class AppUpdateStatus(val value: String) {
    UpToDate("UP-TO-DATE"),
    RecommendedUpdate("RECOMMENDED"),
    ForceUpdate("FORCE-UPDATE");

    companion object {
        fun fromValue(value: String?): AppUpdateStatus =
            entries.find { it.value == value?.trim() } ?: RecommendedUpdate
    }
}