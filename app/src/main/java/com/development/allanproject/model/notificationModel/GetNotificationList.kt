package com.development.allanproject.model.notificationModel

data class GetNotificationList(
    val code: Int,
    val notifications: List<NotificationItem>,
    val status: String,
    val success: Boolean
)