package com.development.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivitySetWorkPreferenceBinding;
import com.development.views.adapter.ReferenceListAdapter;
import com.development.views.adapter.SetWorkPreferenceAdapter;
import com.development.views.model.ReferenceListClass;
import com.development.views.model.SetWorkPreferenceClass;

import java.util.ArrayList;
import java.util.List;

public class SetWorkPreference extends BaseActivity implements View.OnClickListener {
ActivitySetWorkPreferenceBinding workPreferenceBinding;
    SetWorkPreferenceAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workPreferenceBinding = DataBindingUtil.setContentView(this,R.layout.activity_set_work_preference);
         setClickListener();
    }

    private void setClickListener(){
        workPreferenceBinding.back.setOnClickListener(this);
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

        List<SetWorkPreferenceClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            SetWorkPreferenceClass reference = new  SetWorkPreferenceClass();
            reference.setName("Facility Name");
            dataList.add(reference);
        }
        adapter = new SetWorkPreferenceAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        workPreferenceBinding.recyclerView.setLayoutManager(mLayoutManager);
        workPreferenceBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        workPreferenceBinding.recyclerView.setAdapter(adapter);
    }
}
