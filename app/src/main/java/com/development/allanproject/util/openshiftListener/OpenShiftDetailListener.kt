package com.development.allanproject.util.openshiftListener

import com.development.allanproject.model.openshiftModel.GetOpenShift
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail

interface OpenShiftDetailListener {
    fun onStarted()
    fun onSuccess(response: GetOpenShiftDetail)
    fun onFailure(message: String)

}