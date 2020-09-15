package com.development.allanproject.views.activity.ui.profileSummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.appointment.BoookAppointmentViewModel

class ProfileSumViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileSummaryViewModel(repository) as T
    }
}