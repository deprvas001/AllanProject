package com.development.allanproject.data.network

import com.development.allanproject.constant.ApiConstant
import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.commonapi.CityList
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.login.LoginPost
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.signupModel.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ServiceApi {

//    @FormUrlEncoded
//    @POST("update-user")
//    suspend fun userLogin(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Response<SignResponse>

    @GET("nurse_city_list")
    suspend fun getCityList(
        @HeaderMap header: HashMap<String, String>
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun userLogin(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PersonalDetailPost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun userDetail(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PersonalDetailPostParam
    ): Response<SignResponse>

    @POST("authenticate")
    suspend fun login(
        @Body details: LoginPost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun documentUpload(
        @HeaderMap header: HashMap<String, String>,
        @Body details: DocumentDetailPost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun addPerference(
        @HeaderMap header: HashMap<String, String>,
        @Body details: SetPreferencePost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun locationUpload(
        @HeaderMap header: HashMap<String, String>,
        @Body details: LocationPreferencePost
    ): Response<SignResponse>

    @GET("appointment_list")
    suspend fun getAppointmentList(
        @HeaderMap header: HashMap<String, String>
    ): Response<AppointmentGetModel>

    @POST("update-user")
    suspend fun postAppointment(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PostAppointment
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun experienceUpload(
        @HeaderMap header: HashMap<String, String>,
        @Body details: ExperiencePost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun workPost(
        @HeaderMap header: HashMap<String, String>,
        @Body details: AddExperiencePost
    ): Response<SignResponse>


    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ServiceApi{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceApi::class.java)
        }
    }
}