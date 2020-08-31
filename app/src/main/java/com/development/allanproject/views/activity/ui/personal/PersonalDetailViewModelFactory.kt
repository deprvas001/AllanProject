package com.development.allanproject.views.activity.ui.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository

class PersonalDetailViewModelFactory(
private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonDetailViewModel(repository) as T
    }
}