package com.development.allanproject.util

import com.development.allanproject.model.signupModel.SignResponse

interface AuthListener {
    fun onStarted()
    fun onSuccess(response: SignResponse)
    fun onFailure(message: String)

}