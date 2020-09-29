package com.development.allanproject.util.myprofileNotificationListener

import com.development.allanproject.model.myprofile.GetMyProfile
import com.development.allanproject.model.profilesettings.GetMyProfileNotification

interface MyProfileNotificationListener {
    fun onStarted()
    fun onSuccess(response: GetMyProfileNotification)
    fun onFailure(message: String)

}