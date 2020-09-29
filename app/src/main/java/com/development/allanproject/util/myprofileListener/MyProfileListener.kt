package com.development.allanproject.util.myprofileListener

import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.myprofile.GetMyProfile

interface MyProfileListener {
    fun onStarted()
    fun onSuccess(response: GetMyProfile)
    fun onFailure(message: String)

}