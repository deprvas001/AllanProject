package com.development.allanproject.model.profilesettings

data class GetMyProfileNotification(
    val additional: String,
    val code: Int,
    val `data`: MyProfileNotificationData,
    val status: String,
    val success: Boolean
)