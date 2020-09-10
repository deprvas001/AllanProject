package com.development.allanproject.views.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityTermsConditionBinding;

public class TermsCondition extends BaseActivity {
ActivityTermsConditionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_condition);
        if(getIntent().getExtras()!=null){
            binding.policy.setText(Html.fromHtml(getIntent().getExtras().getString("tc")));
        }
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
