package com.development.allanproject.data.repository

import com.development.allanproject.data.network.SafeApiRequest
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.login.LoginPost
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