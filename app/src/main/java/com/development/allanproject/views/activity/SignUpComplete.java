package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivitySignUpCompleteBinding;
import com.development.allanproject.views.activity.ui.login.LoginActivity;

public class SignUpComplete extends BaseActivity implements View.OnClickListener {
ActivitySignUpCompleteBinding completeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completeBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_complete);
        completeBinding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_next:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;
        }
    }
}