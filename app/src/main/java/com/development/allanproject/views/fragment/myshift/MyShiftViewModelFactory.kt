package com.development.allanproject.views.fragment.myshift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.fragment.openshift.OpenShiftViewModel

class MyShiftViewModelFactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyShiftViewModel(
            repository
        ) as T
    }
}