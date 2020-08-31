package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityBackgroundInformationBinding;
import com.development.allanproject.adapter.BackgroundInfoAdapter;
import com.development.allanproject.model.BackgroundInformationClass;

import java.util.ArrayList;
import java.util.List;

public class BackgroundInformation extends BaseActivity implements View.OnClickListener {
ActivityBackgroundInformationBinding informationBinding;
    BackgroundInfoAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        informationBinding = DataBindingUtil.setContentView(this,R.layout.activity_background_information);
        setClickListener();
    }

    private void setClickListener(){
        informationBinding.back.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<BackgroundInformationClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            BackgroundInformationClass award = new BackgroundInformationClass();
            award.setName("Have you previously been terminated\n" +
                    "from a position or assignment?");
            dataList.add(award);
        }
        adapter = new BackgroundInfoAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        informationBinding.recyclerView.setLayoutManager(mLayoutManager);
        informationBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        informationBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
