package com.development.allanproject.views.activity.ui.requestShift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShiftModel

class RequestShiftViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RequestShiftViewModel(
            repository
        ) as T
    }
}