package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityPersonalDetailsBinding;

public class PersonalDetails extends BaseActivity implements View.OnClickListener {
ActivityPersonalDetailsBinding detailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_personal_details);
        detailsBinding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                 startActivity(new Intent(this,AddLicense.class));
                break;
        }
    }
}
