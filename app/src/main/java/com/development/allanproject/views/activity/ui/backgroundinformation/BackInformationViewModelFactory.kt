package com.development.allanproject.views.activity.ui.backgroundinformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.bankinfo.BankViewModel

class BackInformationViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BackgroundInformationViewModel(repository) as T
    }
}