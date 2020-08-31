package com.development.allanproject.views.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;
import com.development.allanproject.databinding.FragmentOpenShiftsBinding;
import com.development.allanproject.views.activity.PreferenceScreen;
import com.development.allanproject.views.activity.RequestedShiftSecond;
import com.development.allanproject.adapter.ViewShiftAdapter;
import com.development.allanproject.model.HiddenJobClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenShifts extends Fragment implements View.OnClickListener {
FragmentOpenShiftsBinding shiftsBinding;
   ViewShiftAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    public OpenShifts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shiftsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_open_shifts, container, false);
        return shiftsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showCustomDialog();
        setClickListener();
    }

    private void setClickListener(){
        setRecyclerView();
        shiftsBinding.btnPreference.setOnClickListener(this);
        shiftsBinding.requestShift.setOnClickListener(this);

    }

    private void setRecyclerView() {

        List<HiddenJobClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            HiddenJobClass job = new HiddenJobClass();
            job.setName("Apolo");
            dataList.add(job);
        }
        adapter = new ViewShiftAdapter(getContext(), dataList);
        mLayoutManager = new LinearLayoutManager(getContext());
        shiftsBinding.recyclerView.setLayoutManager(mLayoutManager);
        shiftsBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        shiftsBinding.recyclerView.setAdapter(adapter);
    }

    private void showCustomDialog(){
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.open_shift_pop_up,null);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_preference:
                startActivity(new Intent(getContext(), PreferenceScreen.class));
                break;

            case R.id.request_shift:
                startActivity(new Intent(getContext(), RequestedShiftSecond.class));
                break;
        }
    }
}
