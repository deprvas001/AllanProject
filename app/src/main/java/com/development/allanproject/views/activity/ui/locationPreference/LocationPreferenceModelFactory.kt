package com.development.allanproject.views.activity.ui.locationPreference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModel

class LocationPreferenceModelFactory(
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationPrefrenceViewModel(repository) as T
    }
}