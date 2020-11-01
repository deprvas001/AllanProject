package com.development.allanproject.util.pastShiftListener

import com.development.allanproject.model.pastShiftModel.MyPastShift
import com.development.allanproject.model.preferenceModel.GetPreferenceList

interface PastShiftListener {
    fun onStarted()
    fun onSuccess(response: MyPastShift)
    fun onFailure(message: String)

}