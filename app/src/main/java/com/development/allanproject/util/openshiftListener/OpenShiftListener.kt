package com.development.allanproject.util.openshiftListener

import com.development.allanproject.model.notificationModel.GetNotificationList
import com.development.allanproject.model.openshiftModel.GetOpenShift

interface OpenShiftListener {
    fun onStarted()
    fun onSuccess(response: GetOpenShift)
    fun onFailure(message: String)

}