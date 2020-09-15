package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityEditWorkExperienceBinding;
import com.development.allanproject.adapter.EditWorkExpAdapter;
import com.development.allanproject.model.EditWorkExpClass;

import java.util.ArrayList;
import java.util.List;

public class EditWorkExperience extends BaseActivity implements View.OnClickListener {
ActivityEditWorkExperienceBinding workExperienceBinding;
    RecyclerView.LayoutManager mLayoutManager;
    EditWorkExpAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workExperienceBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit_work_experience);
        setClickListener();

    }

    private void setClickListener(){
        workExperienceBinding.back.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<EditWorkExpClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            EditWorkExpClass experience = new EditWorkExpClass();
            experience.setName("Active");
            dataList.add(experience);
        }
       /* adapter = new EditWorkExpAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        workExperienceBinding.recyclerView.setLayoutManager(mLayoutManager);
        workExperienceBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        workExperienceBinding.recyclerView.setAdapter(adapter);*/
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
