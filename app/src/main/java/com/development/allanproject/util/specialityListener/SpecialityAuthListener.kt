package com.development.allanproject.util.specialityListener

import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.speciality.GetSpeciality

interface SpecialityAuthListener {
    fun onStarted()
    fun onSuccess(response: GetSpeciality)
    fun onFailure(message: String)
}