package com.development.allanproject.views.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityViewMyShiftBinding;

public class ViewMyShift extends BaseActivity implements View.OnClickListener {
ActivityViewMyShiftBinding myShiftBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myShiftBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_my_shift);
        myShiftBinding.clockIn.setOnClickListener(this);
        myShiftBinding.cancelShift.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clock_in:
                startActivity(new Intent(ViewMyShift.this,ClockInScreen.class));
                break;

            case R.id.cancel_shift:
                showCustomDialog();
                break;
        }
    }

    private void showCustomDialog(){
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.view_future_shift_popup,null);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
