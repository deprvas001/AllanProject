package com.development.allanproject.views.activity.ui.missedShift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.preferedFacility.PreferedFacilityViewModel

class MissedShiftViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MissedShiftViewModel(
            repository
        ) as T
    }
}