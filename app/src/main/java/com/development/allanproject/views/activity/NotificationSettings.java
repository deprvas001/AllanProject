package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityNotificationSettingsBinding;

public class NotificationSettings extends BaseActivity implements View.OnClickListener {
ActivityNotificationSettingsBinding settingsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsBinding = DataBindingUtil.setContentView(this,R.layout.activity_notification_settings);
        settingsBinding.back.setOnClickListener(this);
     }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
