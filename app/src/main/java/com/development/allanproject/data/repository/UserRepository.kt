package com.development.allanproject.data.repository

import com.development.allanproject.data.network.SafeApiRequest
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.model.signupModel.Details
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.model.signupModel.SignResponse

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
}