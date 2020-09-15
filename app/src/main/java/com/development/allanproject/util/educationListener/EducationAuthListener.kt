package com.development.allanproject.util.educationListener

import com.development.allanproject.model.education.EducationListApiResonse

interface EducationAuthListener {
    fun onStarted()
    fun onSuccess(response: EducationListApiResonse)
    fun onFailure(message: String)

}