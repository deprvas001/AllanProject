package com.development.allanproject.util.socialsecurity

import com.development.allanproject.model.reference.ReferenceList
import com.development.allanproject.model.socialsecurity.GetSocialSecurity

interface SocialSecurityListener {
    fun onStarted()
    fun onSuccess(response: GetSocialSecurity)
    fun onFailure(message: String)
}