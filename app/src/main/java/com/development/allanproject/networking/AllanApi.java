package com.development.allanproject.networking;

import com.development.allanproject.model.apiResponse.ApiResponse;
import com.development.allanproject.model.apiResponse.SignUpResponse;
import com.development.allanproject.model.commonapi.CommonApiData;
import com.development.allanproject.model.signupModel.SignUpPostModel;
import com.google.android.gms.common.internal.service.Common;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AllanApi {
    @POST("sign-up")
    Call<SignUpResponse> signUp(
            @Body SignUpPostModel requestModel);

    @POST("util_data")
    Call<CommonApiData> commonApiData();
}
