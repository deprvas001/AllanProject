package com.development.allanproject.util.missedShiftListener

import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.missedShift.GetMissedShift

interface MissedShiftListener {
    fun onStarted()
    fun onSuccess(response: GetMissedShift)
    fun onFailure(message: String)

}