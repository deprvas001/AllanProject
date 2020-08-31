package com.development.allanproject.views.activity.ui.signup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.development.allanproject.R;
import com.development.allanproject.data.session.SessionManager;
import com.development.allanproject.databinding.ActivitySignUpBinding;
import com.development.allanproject.model.commonapi.CommonApiData;
import com.development.allanproject.model.signupModel.SignUpPostModel;
import com.development.allanproject.views.activity.BaseActivity;
import com.development.allanproject.views.activity.TermsCondition;
import com.development.allanproject.views.activity.ui.personal.PersonalDetail;

public class SignUp extends BaseActivity implements View.OnClickListener {
    ActivitySignUpBinding signUpBinding;
    SignUpPostModel postModel=new SignUpPostModel();
    SignUpViewModel viewModel;
    SessionManager session;
    public static  CommonApiData commonApiData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        session = new SessionManager(getApplicationContext());
        signUpBinding.setSignupPost(postModel);
        signUpBinding.termsCondition.setOnClickListener(this);
        signUpBinding.btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.termsCondition:
                startActivity(new Intent(SignUp.this, TermsCondition.class));
                break;

            case R.id.btn_signup:
                if (signUpBinding.inputEmail.getText().toString().isEmpty()) {
                    signUpBinding.signField.setError(getString(R.string.email_empty));
                    return;
                } else if (signUpBinding.inputPhone.getText().toString().isEmpty()) {
                    signUpBinding.phoneField.setError(getString(R.string.phone_empty));
                    return;
                } else if (signUpBinding.inputPassword.getText().toString().isEmpty()) {
                    signUpBinding.passwordField.setError(getString(R.string.password_empty));
                    return;
                } else {
                    signupApiCall();
                }
                break;
        }
    }

    private void signupApiCall() {
        signUpBinding.progressBar.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        viewModel.signUp(this, postModel).observe(this, apiResponse -> {
            signUpBinding.progressBar.setVisibility(View.GONE);
            Toast.makeText(this, String.valueOf(apiResponse.getStatus()), Toast.LENGTH_SHORT).show();

            if(apiResponse.getStatus() == 200){
                if (apiResponse != null) {
                    if (apiResponse.getSignUpResponse().isSuccess()) {
                        int user_id = apiResponse.getSignUpResponse().getUser_id();
                        String token = apiResponse.getSignUpResponse().getAuth_token();
                        session.createLoginSession(String.valueOf(user_id),token);
                         getCommonApiData();


                    }else{
                        showCustomDialog(this,apiResponse.getSignUpResponse().getCode());

                    }
                }
            }else{
                showCustomDialog(this,apiResponse.getStatus());
            }
        });
    }

private void  getCommonApiData(){
    signUpBinding.progressBar.setVisibility(View.VISIBLE);
    viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
    viewModel.commonData().observe(this, apiResponse -> {
        signUpBinding.progressBar.setVisibility(View.GONE);
        Toast.makeText(this, String.valueOf(apiResponse.getStatus()), Toast.LENGTH_SHORT).show();
        if(apiResponse.getCode() == 200){
            if(apiResponse.getSuccess()== true){
                commonApiData = apiResponse;
            }
        }
        startActivity(new Intent(SignUp.this, PersonalDetail.class));
    });
}
}
