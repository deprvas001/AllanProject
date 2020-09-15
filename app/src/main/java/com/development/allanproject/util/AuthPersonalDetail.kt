package com.development.allanproject.util

import com.development.allanproject.model.personalDetail.GetPersonalDetail
import com.development.allanproject.model.signupModel.SignResponse

interface AuthPersonalDetail {
    fun onStarted()
    fun onSuccess(response: GetPersonalDetail)
    fun onFailure(message: String)
}