package com.development.allanproject

import android.app.Application
import com.development.allanproject.data.network.NetworkConnectionInterceptor
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.signupModel.Details
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.views.activity.ui.addexperience.AddExperienceViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.personal.PersonalDetailViewModelFactory
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
        bind() from provider {AddExperienceViewModelFactory(instance())}
        bind() from provider {AddLicenseViewModelFactory(instance())}
        bind() from provider {AddSpecialityViewModelFactory(instance())}

    }
}