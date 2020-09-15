package com.development.allanproject.util.workExperienceListener

import com.development.allanproject.model.experience.GetExperience

interface AuthWorkExperienceListener {
    fun onStarted()
    fun onSuccess(response: GetExperience)
    fun onFailure(message: String)

}