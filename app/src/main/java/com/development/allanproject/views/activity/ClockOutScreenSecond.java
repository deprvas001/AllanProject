package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityClockOutScreenSecondBinding;

public class ClockOutScreenSecond extends BaseActivity implements View.OnClickListener {
ActivityClockOutScreenSecondBinding screenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_clock_out_screen_second);
        screenBinding.clockOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clock_out:
                startActivity(new Intent(this,ClockOutScreen.class));
                break;
        }
    }
}
