package com.development.allanproject

import android.app.Application
import com.development.allanproject.data.network.NetworkConnectionInterceptor
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.appointment.AppointmentModelFactory
import com.development.allanproject.views.activity.ui.editPersonalInfo.EditPersonalViewModelFactory
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModelFactory
import com.development.allanproject.views.activity.ui.locationPreference.LocationPreferenceModelFactory
import com.development.allanproject.views.activity.ui.login.LoginViewModelFactory
import com.development.allanproject.views.activity.ui.personal.PersonalDetailViewModelFactory
import com.development.allanproject.views.activity.ui.profileSummary.ProfileSumViewModelFactory
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication :  Application(), KodeinAware {
    override val kodein = Kodein.lazy{
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ServiceApi(instance()) }
       // bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider {PersonalDetailViewModelFactory(instance())}
        bind() from provider {
            AddExperienceViewModelFactory(
                instance()
            )
        }
        bind() from provider {AddLicenseViewModelFactory(instance())}
        bind() from provider {AddSpecialityViewModelFactory(instance())}
        bind() from provider {LocationPreferenceModelFactory(instance())}
        bind() from provider {LoginViewModelFactory(instance())}
        bind() from provider {AppointmentModelFactory(instance())}
        bind() from provider {ProfileSumViewModelFactory(instance())}
        bind() from provider {EditPersonalViewModelFactory(instance())}
        bind() from provider {EducationViewModelFactory(instance())}
    }
}