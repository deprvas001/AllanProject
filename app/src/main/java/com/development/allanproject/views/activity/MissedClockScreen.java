package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityMissedClockScreenBinding;
import com.development.allanproject.adapter.MissedClockAdapter;
import com.development.allanproject.model.MissedClockClass;

import java.util.ArrayList;
import java.util.List;

public class MissedClockScreen extends BaseActivity implements View.OnClickListener {
   ActivityMissedClockScreenBinding clockScreenBinding;
    MissedClockAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clockScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_missed_clock_screen);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<MissedClockClass> dataList = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            MissedClockClass document = new MissedClockClass();
            document.setName("Forms Name");
            dataList.add(document);
        }
        adapter = new MissedClockAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        clockScreenBinding.recyclerView.setLayoutManager(mLayoutManager);
        clockScreenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        clockScreenBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
