package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityMyProfileBinding;

public class MyProfile extends BaseActivity implements View.OnClickListener {
    ActivityMyProfileBinding profileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        profileBinding.missedClockLayout.setOnClickListener(this);
        profileBinding.editProfileLayout.setOnClickListener(this);
        profileBinding.preferenceScreenLayout.setOnClickListener(this);
        profileBinding.preferedFacilityScreen.setOnClickListener(this);
        profileBinding.myPaymentLayout.setOnClickListener(this);
        profileBinding.trainingLayout.setOnClickListener(this);
        profileBinding.settingsLayout.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.missed_clock_layout:
                startActivity(new Intent(this, MissedClockScreen.class));
                break;

            case R.id.edit_profile_layout:
                startActivity(new Intent(this, EditProfile.class));
                break;

            case R.id.preference_screen_layout:
                startActivity(new Intent(this, PreferenceScreen.class));
                break;

            case R.id.prefered_facility_screen:
                startActivity(new Intent(this, PreferredFacilityScreen.class));
                break;

            case R.id.my_payment_layout:
                startActivity(new Intent(this, MyPayment.class));
                break;

            case R.id.training_layout:
                startActivity(new Intent(this, Training.class));
                break;

            case R.id.settings_layout:
                startActivity(new Intent(this, Settings.class));
                break;        }
    }
}
