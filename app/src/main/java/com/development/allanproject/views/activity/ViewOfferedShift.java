package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityViewOfferedShiftBinding;

public class ViewOfferedShift extends BaseActivity implements View.OnClickListener {
ActivityViewOfferedShiftBinding shiftBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shiftBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_offered_shift);
        shiftBinding.button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                startActivity(new Intent(this,RequestShiftScreen.class));
                break;
        }
    }
}
