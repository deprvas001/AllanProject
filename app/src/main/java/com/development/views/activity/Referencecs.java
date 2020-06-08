package com.development.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityReferencecsBinding;
import com.development.allanproject.databinding.ActivityTrainingBinding;
import com.development.views.adapter.ReferenceListAdapter;
import com.development.views.adapter.TrainingAdapter;
import com.development.views.model.ReferenceListClass;
import com.development.views.model.TrainingClass;

import java.util.ArrayList;
import java.util.List;

public class Referencecs extends BaseActivity implements View.OnClickListener {
    ReferenceListAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityReferencecsBinding referencecsBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referencecsBinding = DataBindingUtil.setContentView(this,R.layout.activity_referencecs);
    }

    private void setClickListener(){
        referencecsBinding.back.setOnClickListener(this);
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

        List<ReferenceListClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            ReferenceListClass reference = new  ReferenceListClass();
            reference.setName("Facility Name");
            dataList.add(reference);
        }
        adapter = new ReferenceListAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        referencecsBinding.recyclerView.setLayoutManager(mLayoutManager);
        referencecsBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        referencecsBinding.recyclerView.setAdapter(adapter);
    }

}
