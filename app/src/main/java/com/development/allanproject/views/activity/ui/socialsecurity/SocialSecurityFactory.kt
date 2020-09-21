package com.development.allanproject.views.activity.ui.socialsecurity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModel

class SocialSecurityFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SocailSecurityViewModel(repository) as T
    }
}