package com.development.allanproject.views.activity.ui.facilityprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.ehrs.EHRSViewModel

class FacilityProfileViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FacilityProfileViewModel(repository) as T
    }
}