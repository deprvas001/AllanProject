package com.development.allanproject.views.activity.ui.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.research.ResearchViewModel

class EditProfileViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditProfileViewModel(repository) as T
    }
}