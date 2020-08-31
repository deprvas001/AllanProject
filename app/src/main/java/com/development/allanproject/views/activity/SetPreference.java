package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivitySetPreferenceBinding;

public class SetPreference extends BaseActivity implements View.OnClickListener {
ActivitySetPreferenceBinding preferenceBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceBinding = DataBindingUtil.setContentView(this,R.layout.activity_set_preference);
        preferenceBinding.btnNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                startActivity(new Intent(this,LocationPreference.class));
                break;
        }
    }
}
