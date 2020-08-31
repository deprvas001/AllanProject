package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityClockInScreenBinding;

public class ClockInScreen extends BaseActivity implements View.OnClickListener {
ActivityClockInScreenBinding screenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_clock_in_screen);
        screenBinding.clockIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clock_in:
                startActivity(new Intent(ClockInScreen.this,ClockOutScreenSecond.class));
                break;
        }
    }
}
