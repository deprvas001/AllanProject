package com.development.allanproject.util.preferenceListener

import com.development.allanproject.model.notificationModel.GetNotificationList
import com.development.allanproject.model.preferenceModel.GetPreferenceList

interface PreferenceListener  {
    fun onStarted()
    fun onSuccess(response: GetPreferenceList)
    fun onFailure(message: String)

}