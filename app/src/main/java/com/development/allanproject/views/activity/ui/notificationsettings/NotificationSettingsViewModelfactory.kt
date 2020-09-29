package com.development.allanproject.views.activity.ui.notificationsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.notificationscreen.NotificationViewModel

class NotificationSettingsViewModelfactory (
    private val repository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotificationSettingViewModel(repository) as T
    }
}