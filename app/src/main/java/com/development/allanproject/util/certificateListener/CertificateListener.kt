package com.development.allanproject.util.certificateListener

import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.certificate.CertificateList

interface CertificateListener {
    fun onStarted()
    fun onSuccess(response: CertificateList)
    fun onFailure(message: String)

}