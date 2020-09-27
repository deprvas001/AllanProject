package com.development.allanproject.views.activity.ui.awards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.language.LanguageViewModel

class AwardViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AwardViewModel(repository) as T
    }
}