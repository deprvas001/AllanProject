package com.development.allanproject.model.facilityprofileModel

data class FacilityProfileData(
    val address: String,
    val bed_count: String,
    val bg_url: String,
    val blocked: Boolean,
    val created_at: String,
    val facility_lists: String,
    val facility_types: String,
    val fax: String,
    val free_parking: Boolean,
    val id: Int,
    val img_url: String,
    val is_verified: Boolean,
    val jcaho_certified: Boolean,
    val lat: Any,
    val long: Any,
    val marked_favorite: Boolean,
    val name: String,
    val organization_id: Int,
    val parent_company: String,
    val phone: String,
    val rating: String,
    val scrub_color: String,
    val social: List<Social>,
    val status: Boolean,
    val updated_at: String,
    val facebook: String,
    val twitter:String,
    val linkedin:String,
    val instagram: String

)