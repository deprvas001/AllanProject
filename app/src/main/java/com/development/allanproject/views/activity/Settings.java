package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivitySettingsBinding;

public class Settings extends BaseActivity implements View.OnClickListener {
ActivitySettingsBinding settingsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsBinding = DataBindingUtil.setContentView(this,R.layout.activity_settings);
        settingsBinding.back.setOnClickListener(this);
        settingsBinding.faq.setOnClickListener(this);
        settingsBinding.help.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.faq:
                startActivity(new Intent(this,FaqScreen.class));
                break;

            case R.id.help:
                break;
        }
    }
}
