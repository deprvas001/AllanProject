package com.development.allanproject.views.activity.ui.viewPastShift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShiftModel

class PastShiftViewModelFactory  (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PastShiftViewModel(
            repository
        ) as T
    }
}