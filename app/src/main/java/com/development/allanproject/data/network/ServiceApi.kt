package com.development.allanproject.data.network

import com.development.allanproject.constant.ApiConstant
import com.development.allanproject.model.adddocumentModel.GetDocumentSpinner
import com.development.allanproject.model.adddocumentModel.PostDocument
import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.bankinfo.BankInfoResponse
import com.development.allanproject.model.bankinfo.PostBankInfo
import com.development.allanproject.model.certificate.CertificateList
import com.development.allanproject.model.education.AddEductionModel
import com.development.allanproject.model.education.EducationListApiResonse
import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.experience.GetExperience
import com.development.allanproject.model.experience.UpdateExperiencePost
import com.development.allanproject.model.form.GetFormList
import com.development.allanproject.model.form.UploadForm
import com.development.allanproject.model.healthDocument.HealthDocPost
import com.development.allanproject.model.healthDocument.HealthDocumentList
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.login.LoginPost
import com.development.allanproject.model.personalDetail.GetPersonalDetail
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.personalDetail.PersonalInfromationUpdate
import com.development.allanproject.model.profileSummary.ProfileSummaryGet
import com.development.allanproject.model.reference.ReferenceList
import com.development.allanproject.model.reference.ReferencePost
import com.development.allanproject.model.signupModel.*
import com.development.allanproject.model.socialsecurity.GetSocialSecurity
import com.development.allanproject.model.socialsecurity.PostSocialSecurity
import com.development.allanproject.model.speciality.GetSpeciality
import com.development.allanproject.model.taxholding.GetTaxHolding
import com.development.allanproject.model.training.GetTrainingPdf
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
    suspend fun updateLicense(
        @HeaderMap header: HashMap<String, String>,
        @Body details: LicenseUpdate
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

    @POST("update-user")
    suspend fun updateBankInfo(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PostBankInfo
    ): Response<SignResponse>

    @GET("appointment_list")
    suspend fun getAppointmentList(
        @HeaderMap header: HashMap<String, String>
    ): Response<AppointmentGetModel>

    @GET("profile_summary")
    suspend fun getProfileSummary(
        @HeaderMap header: HashMap<String, String>,
        @Query("profile_type")type:String
    ): Response<ProfileSummaryGet>

    @GET("nurse_details")
    suspend fun getPersonalDetail(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetPersonalDetail>

    @GET("nurse_details")
    suspend fun getBankInfo(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<BankInfoResponse>

    @GET("nurse_details")
    suspend fun getSpecaility(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetSpeciality>

    @GET("nurse_details")
    suspend fun getEHRS(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<EHRSList>

    @GET("nurse_details")
    suspend fun getPdf(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetTrainingPdf>

    @GET("nurse_details")
    suspend fun getTaxHolding(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetTaxHolding>

    @GET("nurse_details")
    suspend fun getReferenceList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<ReferenceList>

    @GET("nurse_details")
    suspend fun getLicenseList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<ShowLicensesList>

    @GET("nurse_details")
    suspend fun getCertificateList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<CertificateList>

    @GET("nurse_details")
    suspend fun getDocumentList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<HealthDocumentList>

    @GET("nurse_details")
    suspend fun getDocumentSpinnerList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetDocumentSpinner>

    @GET("nurse_details")
    suspend fun getSocialList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetSocialSecurity>

    @GET("nurse_details")
    suspend fun getFormList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetFormList>

    @GET("nurse_details")
    suspend fun getWorkExperienceList(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<GetExperience>

    @GET("nurse_details")
    suspend fun getEducation(
        @HeaderMap header: HashMap<String, String>,
        @Query("step_no")type:String
    ): Response<EducationListApiResonse>

    @POST("update-user")
    suspend fun deleteWorkExperienceList(
        @HeaderMap header: HashMap<String, String>,
        @Body details: DeleteExperience
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun postHealthDoc(
        @HeaderMap header: HashMap<String, String>,
        @Body details: HealthDocPost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun uploadDocument(
        @HeaderMap header: HashMap<String, String>,
        @Body details: UploadForm
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun addDocument(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PostDocument
    ): Response<SignResponse>


    @POST("update-user")
    suspend fun postSecurity(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PostSocialSecurity
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun addEducation(
        @HeaderMap header: HashMap<String, String>,
        @Body details: AddEductionModel
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun updatePersonalInfo(
        @HeaderMap header: HashMap<String, String>,
        @Body details: PersonalInfromationUpdate
    ): Response<SignResponse>

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
    suspend fun referencePost(
        @HeaderMap header: HashMap<String, String>,
        @Body details: ReferencePost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun workPost(
        @HeaderMap header: HashMap<String, String>,
        @Body details: AddExperiencePost
    ): Response<SignResponse>

    @POST("update-user")
    suspend fun workPost(
        @HeaderMap header: HashMap<String, String>,
        @Body details: UpdateExperiencePost
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