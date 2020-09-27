package com.development.allanproject.util.editListener

import com.development.allanproject.model.adddocumentModel.GetDocumentSpinner
import com.development.allanproject.model.editProfile.GetEditProfile

interface EditListener {

    fun onStarted()
    fun onSuccess(response: GetEditProfile)
    fun onFailure(message: String)
}