package com.development.allanproject.views.activity.ui.signup;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.development.allanproject.model.apiResponse.ApiResponse;
import com.development.allanproject.model.commonapi.CommonApiData;
import com.development.allanproject.model.signupModel.SignUpPostModel;

public class SignUpViewModel extends AndroidViewModel{
    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<ApiResponse> signUp(Context context, SignUpPostModel request) {
        return SignUpRepository.getInstance().userSignUp(context,request);
    }

    public MutableLiveData<CommonApiData> commonData() {
        return SignUpRepository.getInstance().commonData();
    }

}
