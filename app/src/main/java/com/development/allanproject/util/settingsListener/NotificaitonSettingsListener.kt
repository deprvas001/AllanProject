package com.development.allanproject.util.settingsListener

import com.development.allanproject.model.notificationModel.GetNotificationSettings
import com.development.allanproject.model.research.GetResearch

interface NotificaitonSettingsListener {
    fun onStarted()
    fun onSuccess(response: GetNotificationSettings)
    fun onFailure(message: String)

}