package com.development.allanproject.views.activity.ui.signup;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.development.allanproject.model.apiResponse.ApiResponse;
import com.development.allanproject.model.apiResponse.SignUpResponse;
import com.development.allanproject.model.commonapi.CityList;
import com.development.allanproject.model.commonapi.CommonApiData;
import com.development.allanproject.model.signupModel.SignUpPostModel;
import com.development.allanproject.model.signupModel.TermsConditionResponse;
import com.development.allanproject.networking.AllanApi;
import com.development.allanproject.networking.RetrofitApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {

    private static SignUpRepository historyRepository = null;
    private AllanApi allanApi;

    public SignUpRepository(){
        allanApi = RetrofitApiService.getRetrofitInstance().create(AllanApi.class);
    }

    public static SignUpRepository getInstance(){
        if(historyRepository == null)
            historyRepository =new SignUpRepository();
        return historyRepository;
    }

    public MutableLiveData<ApiResponse> userSignUp(Context context, SignUpPostModel request){
        final MutableLiveData<ApiResponse> signUpLiveData =new MutableLiveData<>();

        allanApi.signUp(request).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                if(response.code() == 401 || response.code() == 400 || response.code() == 404 || response.code() == 500){
                    try {
                      //  JSONObject jObjError = new JSONObject(response.errorBody().string());
                        signUpLiveData.setValue(new ApiResponse(response.code(),null));

                     //   String message = jObjError.getString("message");
                      //  int status = jObjError.getInt("status");
                       //signUpLiveData.setValue(new ApiResponse(message,status,response.code()));

                        //signUpLiveData.setValue(new ApiResponse(response.code(),null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } /*catch (IOException e) {
                        e.printStackTrace();
                    }*/

                }
                else {
                    if(response.isSuccessful()){
                        signUpLiveData.setValue(new ApiResponse(response.code(),response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
              //  signUpLiveData.setValue(new BookingDetailApiResponse(t));
              //  signUpLiveData.setValue(new ApiResponse(t., null);
            }
        });

        return   signUpLiveData;
    }


    public MutableLiveData<CommonApiData> commonData(){
        final MutableLiveData<CommonApiData> commonLiveData =new MutableLiveData<>();

        allanApi.commonApiData().enqueue(new Callback<CommonApiData>() {
            @Override
            public void onResponse(Call<CommonApiData> call, Response<CommonApiData> response) {


                if(response.code() == 401 || response.code() == 400 || response.code() == 404 || response.code() == 500){
                    try {
                        //  JSONObject jObjError = new JSONObject(response.errorBody().string());
                        commonLiveData.setValue(response.body());

                        //   String message = jObjError.getString("message");
                        //  int status = jObjError.getInt("status");
                        //signUpLiveData.setValue(new ApiResponse(message,status,response.code()));

                        //signUpLiveData.setValue(new ApiResponse(response.code(),null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } /*catch (IOException e) {
                        e.printStackTrace();
                    }*/

                }
                else {
                    if(response.isSuccessful()){
                        commonLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonApiData> call, Throwable t) {
                //  signUpLiveData.setValue(new BookingDetailApiResponse(t));
                //  signUpLiveData.setValue(new ApiResponse(t., null);
            }
        });

        return   commonLiveData;
    }

    public MutableLiveData<TermsConditionResponse> getTermsCondition(){
        final MutableLiveData<TermsConditionResponse> termsLiveData =new MutableLiveData<>();

        allanApi.getTermsCondition().enqueue(new Callback<TermsConditionResponse>() {
            @Override
            public void onResponse(Call<TermsConditionResponse> call, Response<TermsConditionResponse> response) {


                if(response.code() == 401 || response.code() == 400 || response.code() == 404 || response.code() == 500){
                    try {
                        //  JSONObject jObjError = new JSONObject(response.errorBody().string());
                        termsLiveData.setValue(response.body());

                        //   String message = jObjError.getString("message");
                        //  int status = jObjError.getInt("status");
                        //signUpLiveData.setValue(new ApiResponse(message,status,response.code()));

                        //signUpLiveData.setValue(new ApiResponse(response.code(),null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } /*catch (IOException e) {
                        e.printStackTrace();
                    }*/

                }
                else {
                    if(response.isSuccessful()){
                        termsLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<TermsConditionResponse> call, Throwable t) {
                 //  termsLiveData.setValue(t);
                //  signUpLiveData.setValue(new BookingDetailApiResponse(t));
                //  signUpLiveData.setValue(new ApiResponse(t., null);
            }
        });

        return   termsLiveData;
    }

}
