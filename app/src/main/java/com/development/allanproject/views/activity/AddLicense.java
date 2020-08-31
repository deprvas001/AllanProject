package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityAddLicenseBinding;

public class AddLicense extends BaseActivity implements View.OnClickListener {
ActivityAddLicenseBinding licenseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  licenseBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_license);
     //   licenseBinding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
            /* case R.id.btn_next:
                 startActivity(new Intent(this,AddSpecialty.class));
                 break;*/
         }
    }
}
