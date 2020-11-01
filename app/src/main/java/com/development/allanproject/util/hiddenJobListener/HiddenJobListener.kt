package com.development.allanproject.util.hiddenJobListener

import com.development.allanproject.model.healthDocument.HealthDocumentList
import com.development.allanproject.model.hiddenjobs.GetHiddenJobs

interface HiddenJobListener  {
    fun onStarted()
    fun onSuccess(response: GetHiddenJobs)
    fun onFailure(message: String)

}