package com.development.allanproject.views.activity.ui.research

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.awards.AwardViewModel

class ResearchViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResearchViewModel(repository) as T
    }
}