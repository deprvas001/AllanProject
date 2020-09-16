package com.development.allanproject.util.licenseListener

import com.development.allanproject.model.experience.GetExperience
import com.development.allanproject.model.license.ShowLicensesList

interface LicenseAuthListener {
    fun onStarted()
    fun onSuccess(response: ShowLicensesList)
    fun onFailure(message: String)

}