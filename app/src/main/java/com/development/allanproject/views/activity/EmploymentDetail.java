package com.development.allanproject.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityEmploymentDetailBinding;
import com.development.allanproject.views.activity.ui.bankinfo.BankInformation;
import com.development.allanproject.views.activity.ui.i9form.I9FormScreen;
import com.development.allanproject.views.activity.ui.socialsecurity.SocialSecurityList;
import com.development.allanproject.views.activity.ui.socialsecurity.SocialSecurityScreen;
import com.development.allanproject.views.activity.ui.taxholding.TaxHoldingScreen;
import com.development.allanproject.views.activity.ui.uploadid.UploadIDs;

public class EmploymentDetail extends BaseActivity implements View.OnClickListener {
    private ActivityEmploymentDetailBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_employment_detail);
        binding.btnSecurity.setOnClickListener(this);
        binding.btnBank.setOnClickListener(this);
        binding.btnId.setOnClickListener(this);
        binding.btnId.setOnClickListener(this);
        binding.i9.setOnClickListener(this);
        binding.taxHolding.setOnClickListener(this);
      }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_security:
                 intent = new Intent(this, SocialSecurityList.class);
                startActivity(intent);
                break;

            case R.id.btn_bank:
                 intent = new Intent(this, BankInformation.class);
                startActivity(intent);
                break;

            case R.id.btn_id:
                intent = new Intent(this, UploadIDs.class);
                startActivity(intent);
                break;

            case R.id.i_9:
                intent = new Intent(this, I9FormScreen.class);
                startActivity(intent);
                break;

            case R.id.tax_holding:
                intent = new Intent(this, TaxHoldingScreen.class);
                startActivity(intent);
                break;
        }
    }
}
