package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityNormalLoginScreenBinding;
import com.development.allanproject.views.activity.ui.signup.SignUp;

public class NormalLoginScreen extends BaseActivity implements View.OnClickListener {
ActivityNormalLoginScreenBinding screenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_normal_login_screen);
        screenBinding.signup.setOnClickListener(this);
        screenBinding.btnLoginPhone.setOnClickListener(this);
        screenBinding.forgotPassword.setOnClickListener(this);
        screenBinding.btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup:
                startActivity(new Intent(NormalLoginScreen.this, SignUp.class));
                break;

            case R.id.btn_login:
                startActivity(new Intent(NormalLoginScreen.this,HomeScreen.class));
                break;


            case R.id.btn_login_phone:
                startActivity(new Intent(NormalLoginScreen.this,LoginScreen.class));
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(NormalLoginScreen.this,ForgotPassword.class));
                break;
        }
    }
}
