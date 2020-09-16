package com.development.allanproject.data.repository

import com.development.allanproject.data.network.SafeApiRequest
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.education.AddEductionModel
import com.development.allanproject.model.education.EducationListApiResonse
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.experience.GetExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.login.LoginPost
import com.development.allanproject.model.personalDetail.GetPersonalDetail
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.personalDetail.PersonalInfromationUpdate
import com.development.allanproject.model.profileSummary.ProfileSummaryGet
import com.development.allanproject.model.signupModel.*

class UserRepository (
    private val api: ServiceApi
): SafeApiRequest(){
   suspend fun  userDetail( header: HashMap<String, String>,personalDetail: PersonalDetailPostParam) : SignResponse {
       return apiRequest {
           api.userDetail(header,personalDetail)
       }
   }

    suspend fun  userLogin( header: HashMap<String, String>,personalDetail: PersonalDetailPost) : SignResponse {
        return apiRequest {
            api.userLogin(header,personalDetail)
        }
    }

    suspend fun  licenseUpdate( header: HashMap<String, String>,detail: LicenseUpdate) : SignResponse {
        return apiRequest {
            api.updateLicense(header,detail)
        }
    }

    suspend fun  getLicenseList( header: HashMap<String, String>,step: String) : ShowLicensesList {
        return apiRequest {
            api.getLicenseList(header,step)
        }
    }

    suspend fun  getPersonalDetail( header: HashMap<String, String>,step: String) : GetPersonalDetail {
        return apiRequest {
           api.getPersonalDetail(header,step)
        }
    }

    suspend fun  getWorkExperienceList( header: HashMap<String, String>,step: String) : GetExperience {
        return apiRequest {
            api.getWorkExperienceList(header,step)
        }
    }
    suspend fun  getEducationList( header: HashMap<String, String>,step: String) : EducationListApiResonse {
        return apiRequest {
            api.getEducation(header,step)
        }
    }

    suspend fun  deleteWorkExperienceList( header: HashMap<String, String>,workDetail:DeleteExperience) : SignResponse {
        return apiRequest {
            api.deleteWorkExperienceList(header,workDetail)
        }
    }

    suspend fun  addEducation( header: HashMap<String, String>, addEducation: AddEductionModel) : SignResponse {
        return apiRequest {
            api.addEducation(header,addEducation)
        }
    }



    suspend fun  updatePersonalDetail( header: HashMap<String, String>,updatePersonalInfo: PersonalInfromationUpdate) : SignResponse {
        return apiRequest {
            api.updatePersonalInfo(header,updatePersonalInfo)
        }
    }

    suspend fun  login(loginPost: LoginPost) : SignResponse {
        return apiRequest {
            api.login(loginPost)
        }
    }

    suspend fun  documentPost( header: HashMap<String, String>,documentPost: DocumentDetailPost) : SignResponse {
        return apiRequest {
            api.documentUpload(header,documentPost)
        }
    }

    suspend fun  locationPost( header: HashMap<String, String>,locationPost: LocationPreferencePost) : SignResponse {
        return apiRequest {
            api.locationUpload(header,locationPost)
        }
    }

    suspend fun  getProfileSummary( header: HashMap<String, String>,type:String) : ProfileSummaryGet {
        return apiRequest {
            api.getProfileSummary(header,type)
        }
    }

    suspend fun  getAppointment( header: HashMap<String, String>) : AppointmentGetModel {
        return apiRequest {
            api.getAppointmentList(header)
        }
    }

    suspend fun  postAppointment( header: HashMap<String, String>, postAppointment: PostAppointment) : SignResponse {
        return apiRequest {
            api.postAppointment(header, postAppointment)
        }
    }

    suspend fun  getCityList( header: HashMap<String, String>) : SignResponse {
        return apiRequest {
             api.getCityList(header)
        }
    }
    suspend fun  addPerference( header: HashMap<String, String>,documentPost: SetPreferencePost) : SignResponse {
        return apiRequest {
            api.addPerference(header,documentPost)
        }
    }

    suspend fun  experiencePost( header: HashMap<String, String>,documentPost: ExperiencePost) : SignResponse {
        return apiRequest {
            api.experienceUpload(header,documentPost)
        }
    }

    suspend fun  workPost(header: HashMap<String, String>,workExPost: AddExperiencePost) : SignResponse {
        return apiRequest {
            api.workPost(header,workExPost)
        }
    }

}