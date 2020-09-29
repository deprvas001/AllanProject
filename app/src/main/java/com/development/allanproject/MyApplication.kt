package com.development.allanproject

import android.app.Application
import com.development.allanproject.data.network.NetworkConnectionInterceptor
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModelFactory
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModelFactory
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.appointment.AppointmentModelFactory
import com.development.allanproject.views.activity.ui.awards.AwardViewModelFactory
import com.development.allanproject.views.activity.ui.backgroundinformation.BackInformationViewModelFactory
import com.development.allanproject.views.activity.ui.bankinfo.BankViewModelFactory
import com.development.allanproject.views.activity.ui.changepassword.ChangePassViewModelFactory
import com.development.allanproject.views.activity.ui.editPersonalInfo.EditPersonalViewModelFactory
import com.development.allanproject.views.activity.ui.editProfile.EditProfileViewModelFactory
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModelFactory
import com.development.allanproject.views.activity.ui.ehrs.EHRSViewModelFactory
import com.development.allanproject.views.activity.ui.faq.FaqViewModelFactory
import com.development.allanproject.views.activity.ui.healthdocument.HealthDocViewModelFactory
import com.development.allanproject.views.activity.ui.i9form.I9FormViewModelFactory
import com.development.allanproject.views.activity.ui.language.LanguageViewModelFactory
import com.development.allanproject.views.activity.ui.locationPreference.LocationPreferenceModelFactory
import com.development.allanproject.views.activity.ui.login.LoginViewModelFactory
import com.development.allanproject.views.activity.ui.myprofile.MyProfileViewModelFactory
import com.development.allanproject.views.activity.ui.notificationscreen.NotificationViewModelFactory
import com.development.allanproject.views.activity.ui.notificationsettings.NotificationSettingsViewModelfactory
import com.development.allanproject.views.activity.ui.personal.PersonalDetailViewModelFactory
import com.development.allanproject.views.activity.ui.profileSummary.ProfileSumViewModelFactory
import com.development.allanproject.views.activity.ui.profilesetting.ProfileSettingViewModelFactory
import com.development.allanproject.views.activity.ui.reference.ReferenceViewModelFactory
import com.development.allanproject.views.activity.ui.research.ResearchViewModel
import com.development.allanproject.views.activity.ui.research.ResearchViewModelFactory
import com.development.allanproject.views.activity.ui.socialsecurity.SocialSecurityFactory
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import com.development.allanproject.views.activity.ui.taxholding.TaxHoldingViewModelFactory
import com.development.allanproject.views.activity.ui.training.TrainingViewModelFactory
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
        bind() from provider {CertificateViewModelFactory(instance())}
        bind() from provider {HealthDocViewModelFactory(instance())}
        bind() from provider {EHRSViewModelFactory(instance())}
        bind() from provider {ReferenceViewModelFactory(instance())}
        bind() from provider {TrainingViewModelFactory(instance())}
        bind() from provider {BankViewModelFactory(instance())}
        bind() from provider {AddDocumentViewModelFactory(instance())}
        bind() from provider {SocialSecurityFactory(instance())}
        bind() from provider {TaxHoldingViewModelFactory(instance())}
        bind() from provider {ChangePassViewModelFactory(instance())}
        bind() from provider { BackInformationViewModelFactory(instance()) }
        bind() from provider { LanguageViewModelFactory(instance()) }

        bind() from provider { AwardViewModelFactory(instance()) }
        bind() from provider { ResearchViewModelFactory(instance()) }
        bind() from provider { EditProfileViewModelFactory(instance()) }
        bind() from provider { FaqViewModelFactory(instance()) }

        bind() from provider { MyProfileViewModelFactory(instance()) }

        bind() from provider { NotificationViewModelFactory(instance()) }
        bind() from provider { ProfileSettingViewModelFactory(instance()) }

        bind() from provider { NotificationSettingsViewModelfactory(instance()) }
        bind() from provider { I9FormViewModelFactory(instance()) }

    }
}