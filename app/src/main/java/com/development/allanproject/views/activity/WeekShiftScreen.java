package com.development.allanproject.views.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityWeekShiftScreenBinding;
import com.development.allanproject.adapter.WeekShiftAdapter;
import com.development.allanproject.model.WeekShiftClass;

import java.util.ArrayList;
import java.util.List;

public class WeekShiftScreen extends BaseActivity implements View.OnClickListener {
    ActivityWeekShiftScreenBinding shiftScreenBinding;
    WeekShiftAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shiftScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_week_shift_screen);
     //   setRecyclerView();
        showCustomDialog();
        shiftScreenBinding.back.setOnClickListener(this);
    }

    private void setRecyclerView() {

        List<WeekShiftClass> dataList = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            WeekShiftClass document = new WeekShiftClass();
            document.setName("Forms Name");
            dataList.add(document);
        }
        adapter = new WeekShiftAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        shiftScreenBinding.recyclerView.setLayoutManager(mLayoutManager);
        shiftScreenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        shiftScreenBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void showCustomDialog(){
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.rate_dialog,null);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
