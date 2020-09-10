package com.development.allanproject.data.repository

import com.development.allanproject.data.network.SafeApiRequest
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.login.LoginPost
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.signupModel.*

class UserRepository (
    private val api: ServiceApi
): SafeApiRequest(){
   /* suspend fun  userLogin(email: String, password: String) : SignResponse{
        return apiRequest {
            api.userLogin(email, password)
        }

    }
*/
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