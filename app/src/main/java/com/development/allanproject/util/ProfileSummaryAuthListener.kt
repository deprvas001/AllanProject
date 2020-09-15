package com.development.allanproject.util

import com.development.allanproject.model.profileSummary.ProfileSummaryGet
import com.development.allanproject.model.signupModel.SignResponse

interface ProfileSummaryAuthListener{
fun onStarted()
fun onSuccess(response: ProfileSummaryGet)
fun onFailure(message: String)
}