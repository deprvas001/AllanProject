package com.development.allanproject.views.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicy extends BaseActivity {
ActivityPrivacyPolicyBinding policyBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        policyBinding = DataBindingUtil.setContentView(this,R.layout.activity_privacy_policy);
        if(getIntent().getExtras()!=null){
            policyBinding.privacyText.setText(Html.fromHtml(getIntent().getExtras().getString("policy")));
        }
        policyBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
