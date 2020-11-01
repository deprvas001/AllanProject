package com.development.allanproject.views.activity.ui.hiddenjob

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.viewPastShift.PastShiftViewModel

class HiddenViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HiddenJobViewModel(
            repository
        ) as T
    }
}