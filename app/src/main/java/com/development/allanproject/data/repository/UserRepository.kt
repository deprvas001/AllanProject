package com.development.allanproject.data.repository

import com.development.allanproject.data.network.SafeApiRequest
import com.development.allanproject.data.network.ServiceApi
import com.development.allanproject.model.adddocumentModel.GetDocumentSpinner
import com.development.allanproject.model.adddocumentModel.PostDocument
import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.award.GetAward
import com.development.allanproject.model.award.PostAward
import com.development.allanproject.model.backinformation.GetBackgroundData
import com.development.allanproject.model.backinformation.PostBackgroundInformation
import com.development.allanproject.model.bankinfo.BankInfoResponse
import com.development.allanproject.model.bankinfo.PostBankInfo
import com.development.allanproject.model.certificate.CertificateList
import com.development.allanproject.model.editProfile.GetEditProfile
import com.development.allanproject.model.education.AddEductionModel
import com.development.allanproject.model.education.EducationListApiResonse
import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.experience.GetExperience
import com.development.allanproject.model.experience.UpdateExperiencePost
import com.development.allanproject.model.facilityprofileModel.GetFacilityProfile
import com.development.allanproject.model.faq.GetFaqList
import com.development.allanproject.model.form.GetFormList
import com.development.allanproject.model.form.UploadForm
import com.development.allanproject.model.healthDocument.HealthDocPost
import com.development.allanproject.model.healthDocument.HealthDocumentList
import com.development.allanproject.model.i9form.GetI9Form
import com.development.allanproject.model.i9form.PostI9Form
import com.development.allanproject.model.lanugage.GetLanugage
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.login.LoginPost
import com.development.allanproject.model.myprofile.GetMyProfile
import com.development.allanproject.model.notificationModel.GetNotificationList
import com.development.allanproject.model.notificationModel.GetNotificationSettings
import com.development.allanproject.model.notificationModel.PostNotificationSettings
import com.development.allanproject.model.personalDetail.GetPersonalDetail
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.personalDetail.PersonalInfromationUpdate
import com.development.allanproject.model.preferenceModel.GetPreferenceList
import com.development.allanproject.model.profileSummary.ProfileSummaryGet
import com.development.allanproject.model.profilesettings.GetMyProfileNotification
import com.development.allanproject.model.reference.ReferenceList
import com.development.allanproject.model.reference.ReferencePost
import com.development.allanproject.model.research.GetResearch
import com.development.allanproject.model.research.PostResearch
import com.development.allanproject.model.signupModel.*
import com.development.allanproject.model.socialsecurity.GetSocialSecurity
import com.development.allanproject.model.socialsecurity.PostSocialSecurity
import com.development.allanproject.model.speciality.GetSpeciality
import com.development.allanproject.model.taxholding.GetTaxHolding
import com.development.allanproject.model.taxholding.PostTaxData
import com.development.allanproject.model.training.GetTrainingPdf

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

    suspend fun  bankInfo( header: HashMap<String, String>,step: String) : BankInfoResponse {
        return apiRequest {
            api.getBankInfo(header,step)
        }
    }

    suspend fun  backgroundInfo( header: HashMap<String, String>,step: String) : GetBackgroundData {
        return apiRequest {
            api.getBackInfo(header,step)
        }
    }

    suspend fun  languageInfo( header: HashMap<String, String>,step: String) : GetLanugage {
        return apiRequest {
            api.getLang(header,step)
        }
    }

    suspend fun getAward( header: HashMap<String, String>,step: String) : GetAward {
        return apiRequest {
            api.getAward(header,step)
        }
    }

    suspend fun getPreference( header: HashMap<String, String>,step: String) : GetPreferenceList {
        return apiRequest {
            api.getPreferenceList(header,step)
        }
    }

    suspend fun getFaq( header: HashMap<String, String>) : GetFaqList {
        return apiRequest {
            api.getFaq(header)
        }
    }

    suspend fun getMyProfile( header: HashMap<String, String>,step: String) : GetMyProfile{
        return apiRequest {
            api.getMyProife(header,step)
        }
    }


    suspend fun getNotificationProfile( header: HashMap<String, String>,step: String) : GetMyProfileNotification{
        return apiRequest {
            api.getNotificationProfile(header,step)
        }
    }

    suspend fun getNotificationSettings( header: HashMap<String, String>,step: String) : GetNotificationSettings{
        return apiRequest {
            api.getNotificationSettings(header,step)
        }
    }

    suspend fun userLogout( header: HashMap<String, String>) : SignResponse{
        return apiRequest {
            api.userLogout(header)
        }
    }

    suspend fun updateNotificationProfile( header: HashMap<String, String>,detail: HashMap<String, Any>) : SignResponse{
        return apiRequest {
            api.updateMyProfileNotification(header,detail)
        }
    }

    suspend fun forgotPassword( header: HashMap<String, String>,detail: HashMap<String, Any>) : SignResponse{
        return apiRequest {
            api.forgotPassword(header,detail)
        }
    }

    suspend fun updatePreference( header: HashMap<String, String>,detail: HashMap<String, Any>) : SignResponse{
        return apiRequest {
            api.updatePreference(header,detail)
        }
    }


    suspend fun getNotification( header: HashMap<String, String>) : GetNotificationList{
        return apiRequest {
            api.getNotification(header)
        }
    }

    suspend fun getResearch( header: HashMap<String, String>,step: String) : GetResearch {
        return apiRequest {
            api.getResearch(header,step)
        }
    }

    suspend fun getProfile( header: HashMap<String, String>,step: String) : GetEditProfile {
        return apiRequest {
            api.getEditProfile(header,step)
        }
    }

    suspend fun   updatebankInfo( header: HashMap<String, String>,info: PostBankInfo) :SignResponse {
        return apiRequest {
            api.updateBankInfo(header,info)
        }
    }

    suspend fun   updatebackgroundInfo( header: HashMap<String, String>,info: PostBackgroundInformation) :SignResponse {
        return apiRequest {
            api.updateBankroundInfo(header,info)
        }
    }

    suspend fun   updateAward( header: HashMap<String, String>,info: PostAward) :SignResponse {
        return apiRequest {
            api.updateAward(header,info)
        }
    }

    suspend fun   postResearch( header: HashMap<String, String>,info: PostResearch) :SignResponse {
        return apiRequest {
            api.updateResearch(header,info)
        }
    }

    suspend fun   postNotification( header: HashMap<String, String>,info: PostNotificationSettings) :SignResponse {
        return apiRequest {
            api.postNotification(header,info)
        }
    }

    suspend fun   changePassword( header: HashMap<String, String>, detail: HashMap<String, String>) :SignResponse {
        return apiRequest {
            api.changePassword(header,detail)
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

    suspend fun  getCertificateList( header: HashMap<String, String>,step: String) : CertificateList {
        return apiRequest {
            api.getCertificateList(header,step)
        }
    }


    suspend fun  getDocumentList( header: HashMap<String, String>,step: String) : GetDocumentSpinner {
        return apiRequest {
            api.getDocumentSpinnerList(header,step)
        }
    }

    suspend fun  getSecurityList( header: HashMap<String, String>,step: String) : GetSocialSecurity {
        return apiRequest {
            api.getSocialList(header,step)
        }
    }

    suspend fun  getFormList( header: HashMap<String, String>,step: String) : GetFormList {
        return apiRequest {
            api.getFormList(header,step)
        }
    }

    suspend fun  uploadDoc( header: HashMap<String, String>,detail: UploadForm) :SignResponse {
        return apiRequest {
            api.uploadDocument(header,detail)
        }
    }

    suspend fun  getHealthDocList( header: HashMap<String, String>,step: String) : HealthDocumentList{
        return apiRequest {
            api.getDocumentList(header,step)
        }
    }

    suspend fun  postHealthDoc( header: HashMap<String, String>,detail: HealthDocPost) : SignResponse{
        return apiRequest {
            api.postHealthDoc(header,detail)
        }
    }

    suspend fun  addDocument( header: HashMap<String, String>,detail: PostDocument) : SignResponse{
        return apiRequest {
            api.addDocument(header,detail)
        }
    }

    suspend fun postSecurity( header: HashMap<String, String>,detail: PostSocialSecurity) : SignResponse{
        return apiRequest {
            api.postSecurity(header,detail)
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

    suspend fun  getSpeciality( header: HashMap<String, String>,step: String) : GetSpeciality {
        return apiRequest {
            api.getSpecaility(header,step)
        }
    }

    suspend fun  getEHRS( header: HashMap<String, String>,step: String) : EHRSList {
        return apiRequest {
            api.getEHRS(header,step)
        }
    }

    suspend fun  getFacilityProfile( header: HashMap<String, String>,id: String) : GetFacilityProfile {
        return apiRequest {
            api.getFacilityProfile(header,id)
        }
    }

    suspend fun  getPdf( header: HashMap<String, String>,step: String) : GetTrainingPdf {
        return apiRequest {
            api.getPdf(header,step)
        }
    }

    suspend fun  getTaxHolding( header: HashMap<String, String>,step: String) : GetTaxHolding {
        return apiRequest {
            api.getTaxHolding(header,step)
        }
    }

    suspend fun  getI9Form( header: HashMap<String, String>,step: String) : GetI9Form {
        return apiRequest {
            api.getI9Form(header,step)
        }
    }


    suspend fun  postTaxHolding( header: HashMap<String, String>,post: PostTaxData) : SignResponse {
        return apiRequest {
            api.updateTaxInfo(header,post)
        }
    }

    suspend fun  postI9Form( header: HashMap<String, String>,post: PostI9Form) : SignResponse {
        return apiRequest {
            api.postI9Form(header,post)
        }
    }

    suspend fun  getReferenceList( header: HashMap<String, String>,step: String) : ReferenceList {
        return apiRequest {
            api.getReferenceList(header,step)
        }
    }

    suspend fun  experiencePost( header: HashMap<String, String>,documentPost: ExperiencePost) : SignResponse {
        return apiRequest {
            api.experienceUpload(header,documentPost)
        }
    }

    suspend fun  referencePost( header: HashMap<String, String>,post: ReferencePost) : SignResponse {
        return apiRequest {
            api.referencePost(header,post)
        }
    }

    suspend fun  workPost(header: HashMap<String, String>,workExPost: AddExperiencePost) : SignResponse {
        return apiRequest {
            api.workPost(header,workExPost)
        }
    }

    suspend fun  updateWorkPost(header: HashMap<String, String>,workExPost: UpdateExperiencePost) : SignResponse {
        return apiRequest {
            api.workPost(header,workExPost)
        }
    }

}