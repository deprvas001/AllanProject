package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityAddExperienceBinding;

public class AddExperience extends BaseActivity implements View.OnClickListener {
ActivityAddExperienceBinding experienceBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        experienceBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_experience);
        experienceBinding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                startActivity(new Intent(this,AddExperienceInfo.class));
                break;


        }
    }
}
