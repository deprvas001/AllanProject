package com.development.allanproject.model.notificationModel

data class GetNotificationSettings(
    val additional: String,
    val code: Int,
    val `data`: NotificationSettingsData,
    val status: String,
    val success: Boolean
)