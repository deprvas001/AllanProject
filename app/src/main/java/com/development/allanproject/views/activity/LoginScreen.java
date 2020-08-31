package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityLoginScreenBinding;
import com.development.allanproject.views.activity.ui.signup.SignUp;

public class LoginScreen extends BaseActivity {
ActivityLoginScreenBinding screenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_screen);
        screenBinding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, SignUp.class));
            }
        });

        screenBinding.btnLoginOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this,OTPVerification.class));
            }
        });


    }
}
