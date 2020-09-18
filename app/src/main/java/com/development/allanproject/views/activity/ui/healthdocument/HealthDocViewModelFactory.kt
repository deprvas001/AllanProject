package com.development.allanproject.views.activity.ui.healthdocument

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModel

class HealthDocViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HealthDocumentViewModel(repository) as T
    }
}