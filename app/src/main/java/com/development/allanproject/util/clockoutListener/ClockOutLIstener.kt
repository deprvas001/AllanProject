package com.development.allanproject.util.clockoutListener

import com.development.allanproject.model.clockoutModel.GetClockOutDetail
import com.development.allanproject.model.editProfile.GetEditProfile

interface ClockOutLIstener {

    fun onStarted()
    fun onSuccess(response: GetClockOutDetail)
    fun onFailure(message: String)
}