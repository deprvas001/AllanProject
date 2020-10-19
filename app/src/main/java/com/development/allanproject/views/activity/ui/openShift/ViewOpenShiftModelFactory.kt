package com.development.allanproject.views.activity.ui.openShift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.fragment.openshift.OpenShiftViewModel

class ViewOpenShiftModelFactory  (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewOpenShiftModel(
            repository
        ) as T
    }
}