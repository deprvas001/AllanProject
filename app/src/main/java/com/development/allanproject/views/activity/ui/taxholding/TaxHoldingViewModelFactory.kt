package com.development.allanproject.views.activity.ui.taxholding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.training.TrainingViewModel

class TaxHoldingViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaxHoldingViewModel(repository) as T
    }
}