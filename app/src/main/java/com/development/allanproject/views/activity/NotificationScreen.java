package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityNotificationScreenBinding;
import com.development.allanproject.adapter.NotificationAdapter;
import com.development.allanproject.model.NotificationClass;

import java.util.ArrayList;
import java.util.List;

public class NotificationScreen extends BaseActivity implements View.OnClickListener {
ActivityNotificationScreenBinding screenBinding;
    NotificationAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_notification_screen);
        setClickListener();
    }

    private void setClickListener(){
         screenBinding.back.setOnClickListener(this);
        setRecyclerView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void setRecyclerView() {

        List<NotificationClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            NotificationClass reference = new  NotificationClass();
            reference.setName("Notification Name");
            dataList.add(reference);
        }
        adapter = new NotificationAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
         screenBinding.recyclerView.setLayoutManager(mLayoutManager);
         screenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
         screenBinding.recyclerView.setAdapter(adapter);
    }

}
