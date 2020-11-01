package com.development.allanproject.networking;

import com.development.allanproject.model.apiResponse.ApiResponse;
import com.development.allanproject.model.apiResponse.SignUpResponse;
import com.development.allanproject.model.commonapi.CityList;
import com.development.allanproject.model.commonapi.CommonApiData;
import com.development.allanproject.model.signupModel.SignUpPostModel;
import com.development.allanproject.model.signupModel.TermsConditionResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AllanApi {
    @POST("sign-up")
    Call<SignUpResponse> signUp(
            @Body SignUpPostModel requestModel);

    @POST("util_data")
    Call<CommonApiData> commonApiData();

    @GET("policy_and_tc")
    Call<TermsConditionResponse> getTermsCondition();

    @GET("nurse_city_list")
    Call<CityList> getCityList(
            @HeaderMap HashMap<String, String> headers
    );
}
