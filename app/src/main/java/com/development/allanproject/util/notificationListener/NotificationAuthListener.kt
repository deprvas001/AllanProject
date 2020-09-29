package com.development.allanproject.util.notificationListener

import com.development.allanproject.model.myprofile.GetMyProfile
import com.development.allanproject.model.notificationModel.GetNotificationList

interface NotificationAuthListener {
    fun onStarted()
    fun onSuccess(response: GetNotificationList)
    fun onFailure(message: String)

}