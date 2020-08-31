package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityTrainingBinding;
import com.development.allanproject.adapter.TrainingAdapter;
import com.development.allanproject.model.TrainingClass;

import java.util.ArrayList;
import java.util.List;

public class Training extends BaseActivity implements View.OnClickListener {
    TrainingAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityTrainingBinding trainingBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainingBinding = DataBindingUtil.setContentView(this,R.layout.activity_training);
        setClickListener();
    }

    private void setClickListener(){
        trainingBinding.back.setOnClickListener(this);
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

        List<TrainingClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            TrainingClass trainingClass = new TrainingClass();
            trainingClass.setText("Dummy Text");
            dataList.add(trainingClass);
        }
        adapter = new TrainingAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        trainingBinding.recyclerView.setLayoutManager(mLayoutManager);
        trainingBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        trainingBinding.recyclerView.setAdapter(adapter);
    }

}
