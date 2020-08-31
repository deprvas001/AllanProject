package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityAddExperienceInfoBinding;

public class AddExperienceInfo extends BaseActivity implements View.OnClickListener {
ActivityAddExperienceInfoBinding infoBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_experience_info);
        infoBinding.btnNext.setOnClickListener(this);
     }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                startActivity(new Intent(this,SetFacilityType.class));
                break;
        }
    }
}
