package com.development.allanproject.util.trainingListener

import com.development.allanproject.model.speciality.GetSpeciality
import com.development.allanproject.model.training.GetTrainingPdf

interface TrainingAuthListener {
    fun onStarted()
    fun onSuccess(response: GetTrainingPdf)
    fun onFailure(message: String)
}