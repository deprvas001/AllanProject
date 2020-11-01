package com.development.allanproject.views.activity.ui.clockInShift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.viewPastShift.PastShiftViewModel

class ClockShiftViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClockShiftViewModel(
            repository
        ) as T
    }
}